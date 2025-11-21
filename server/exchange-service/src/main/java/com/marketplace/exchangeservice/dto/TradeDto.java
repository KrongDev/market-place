package com.marketplace.exchangeservice.dto;

import com.marketplace.exchangeservice.domain.TradeStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

public class TradeDto {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RegisterRequest {
        private Long sellerId;
        private String itemName;
        private String category;
        private String rarity;
        private Map<String, Object> options;
        private BigDecimal price;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private String id;
        private Long sellerId;
        private Long buyerId;
        private String itemName;
        private Map<String, Object> options;
        private BigDecimal price;
        private TradeStatus status;
        private LocalDateTime createdAt;
    }
}
