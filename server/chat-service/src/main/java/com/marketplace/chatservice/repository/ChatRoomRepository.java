package com.marketplace.chatservice.repository;

import com.marketplace.chatservice.domain.ChatRoom;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends MongoRepository<ChatRoom, String> {
    Optional<ChatRoom> findByTradeId(String tradeId);
    List<ChatRoom> findBySellerIdOrBuyerId(Long sellerId, Long buyerId);
}
