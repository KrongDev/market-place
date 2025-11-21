package com.marketplace.exchangeservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Document(collection = "transaction_records")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRecord {

    @Id
    private String id;

    private String tradeId;
    private Long sellerId;
    private Long buyerId;
    private BigDecimal price;
    private String currencyType; // e.g., "GOLD", "USD"

    private LocalDateTime completedAt;
}
