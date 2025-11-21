package com.marketplace.chatservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Setter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "chat_rooms")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoom {

    @Id
    private String id;

    private String tradeId;
    private Long sellerId;
    private Long buyerId;

    @Builder.Default
    private boolean sellerAgreed = false;
    @Builder.Default
    private boolean buyerAgreed = false;

    @CreatedDate
    private LocalDateTime createdAt;
}
