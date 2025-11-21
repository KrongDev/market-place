package com.marketplace.chatservice.controller;

import com.marketplace.common.dto.ApiResponse;
import com.marketplace.chatservice.dto.ChatDto;
import com.marketplace.chatservice.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;

    // WebSocket Endpoint
    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@Payload ChatDto.SendMessageRequest request) {
        ChatDto.MessageResponse response = chatService.saveMessage(request);
        // Broadcast to subscribers of /topic/room/{roomId}
        messagingTemplate.convertAndSend("/topic/room/" + request.getRoomId(), response);
    }

    // REST Endpoints
    @PostMapping("/api/v1/chat/rooms")
    public ApiResponse<String> createRoom(@RequestBody ChatDto.CreateRoomRequest request) {
        return ApiResponse.success(chatService.createRoom(request));
    }

    @GetMapping("/api/v1/chat/rooms")
    public ApiResponse<List<ChatDto.RoomResponse>> getMyRooms(@RequestParam Long userId) {
        return ApiResponse.success(chatService.getMyRooms(userId));
    }

    @GetMapping("/api/v1/chat/rooms/{roomId}/messages")
    public ApiResponse<List<ChatDto.MessageResponse>> getMessages(@PathVariable String roomId) {
        return ApiResponse.success(chatService.getMessages(roomId));
    }
    @PostMapping("/api/v1/chat/rooms/{roomId}/complete")
    public ApiResponse<Void> completeTrade(@PathVariable String roomId, @RequestParam Long userId) {
        chatService.markComplete(roomId, userId);
        return ApiResponse.success(null);
    }
}
