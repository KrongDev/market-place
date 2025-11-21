package com.marketplace.chatservice.service;

import com.marketplace.common.exception.BusinessException;
import com.marketplace.common.exception.GlobalErrorCode;
import com.marketplace.chatservice.domain.ChatMessage;
import com.marketplace.chatservice.domain.ChatRoom;
import com.marketplace.chatservice.dto.ChatDto;
import com.marketplace.chatservice.repository.ChatMessageRepository;
import com.marketplace.chatservice.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;

    @Transactional
    public String createRoom(ChatDto.CreateRoomRequest request) {
        // Check if room already exists for this trade
        return chatRoomRepository.findByTradeId(request.getTradeId())
                .map(ChatRoom::getId)
                .orElseGet(() -> {
                    ChatRoom room = ChatRoom.builder()
                            .tradeId(request.getTradeId())
                            .sellerId(request.getSellerId())
                            .buyerId(request.getBuyerId())
                            .build();
                    return chatRoomRepository.save(room).getId();
                });
    }

    @Transactional
    public ChatDto.MessageResponse saveMessage(ChatDto.SendMessageRequest request) {
        ChatMessage message = ChatMessage.builder()
                .roomId(request.getRoomId())
                .senderId(request.getSenderId())
                .message(request.getMessage())
                .build();

        ChatMessage saved = chatMessageRepository.save(message);
        
        return ChatDto.MessageResponse.builder()
                .id(saved.getId())
                .roomId(saved.getRoomId())
                .senderId(saved.getSenderId())
                .message(saved.getMessage())
                .sentAt(saved.getSentAt())
                .build();
    }

    @Transactional(readOnly = true)
    public List<ChatDto.RoomResponse> getMyRooms(Long userId) {
        return chatRoomRepository.findBySellerIdOrBuyerId(userId, userId).stream()
                .map(room -> ChatDto.RoomResponse.builder()
                        .id(room.getId())
                        .tradeId(room.getTradeId())
                        .sellerId(room.getSellerId())
                        .buyerId(room.getBuyerId())
                        .createdAt(room.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ChatDto.MessageResponse> getMessages(String roomId) {
        return chatMessageRepository.findByRoomIdOrderBySentAtAsc(roomId).stream()
                .map(msg -> ChatDto.MessageResponse.builder()
                        .id(msg.getId())
                        .roomId(msg.getRoomId())
                        .senderId(msg.getSenderId())
                        .message(msg.getMessage())
                        .sentAt(msg.getSentAt())
                        .build())
                .collect(Collectors.toList());
    }
    @Transactional
    public void markComplete(String roomId, Long userId) {
        ChatRoom room = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new BusinessException(GlobalErrorCode.NOT_FOUND, "Room not found"));

        if (room.getSellerId().equals(userId)) {
            room.setSellerAgreed(true);
        } else if (room.getBuyerId().equals(userId)) {
            room.setBuyerAgreed(true);
        } else {
            throw new BusinessException(GlobalErrorCode.FORBIDDEN, "Not a participant");
        }

        chatRoomRepository.save(room);

        if (room.isSellerAgreed() && room.isBuyerAgreed()) {
            archiveAndClose(room);
        }
    }

    private void archiveAndClose(ChatRoom room) {
        // 1. Fetch all messages
        List<ChatMessage> messages = chatMessageRepository.findByRoomIdOrderBySentAtAsc(room.getId());
        
        // 2. Convert to JSON (Simplified for demo)
        String archiveContent = messages.toString(); // In real app, use ObjectMapper
        
        // 3. Upload to File Service (TODO: Implement Feign/RestTemplate)
        System.out.println("Archiving chat for trade " + room.getTradeId() + ": " + archiveContent);
        
        // 4. Notify Exchange Service (TODO: Implement Feign/RestTemplate)
        System.out.println("Completing trade " + room.getTradeId());
        
        // 5. Delete Room and Messages
        chatMessageRepository.deleteAll(messages);
        chatRoomRepository.delete(room);
    }
}
