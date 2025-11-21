package com.marketplace.currencyservice.dto;

import com.marketplace.currencyservice.domain.ListingStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CurrencyDto {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateListingRequest {
        private Long sellerId;
        private Long amount;
        private BigDecimal price;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ListingResponse {
        private Long id;
        private Long sellerId;
        private Long amount;
        private BigDecimal price;
        private ListingStatus status;
        private LocalDateTime createdAt;
    }
}
