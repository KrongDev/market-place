package com.marketplace.exchangeservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Document(collection = "trades")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Trade {

    @Id
    private String id;

    private Long sellerId;
    private Long buyerId;

    private Item item;
    private BigDecimal price;

    private TradeStatus status;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public void request(Long buyerId) {
        this.buyerId = buyerId;
        this.status = TradeStatus.REQUESTED;
    }

    public void accept() {
        this.status = TradeStatus.ACCEPTED;
    }

    public void complete() {
        this.status = TradeStatus.COMPLETED;
    }
    
    public void cancel() {
        this.status = TradeStatus.CANCELLED;
    }
}
