package com.marketplace.chatservice.dto;

import lombok.*;

import java.time.LocalDateTime;

public class ChatDto {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateRoomRequest {
        private String tradeId;
        private Long sellerId;
        private Long buyerId;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SendMessageRequest {
        private String roomId;
        private Long senderId;
        private String message;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RoomResponse {
        private String id;
        private String tradeId;
        private Long sellerId;
        private Long buyerId;
        private LocalDateTime createdAt;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MessageResponse {
        private String id;
        private String roomId;
        private Long senderId;
        private String message;
        private LocalDateTime sentAt;
    }
}
