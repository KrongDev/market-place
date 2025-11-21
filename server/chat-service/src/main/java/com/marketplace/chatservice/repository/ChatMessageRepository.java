package com.marketplace.chatservice.repository;

import com.marketplace.chatservice.domain.ChatMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {
    List<ChatMessage> findByRoomIdOrderBySentAtAsc(String roomId);
}
