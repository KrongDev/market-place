package com.marketplace.currencyservice.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "currency_listings")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class CurrencyListing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long sellerId;

    private Long buyerId;

    @Column(nullable = false)
    private Long currencyAmount; // e.g., 1000 Gold

    @Column(nullable = false)
    private BigDecimal price; // e.g., 10.00 USD

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ListingStatus status;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    public void request(Long buyerId) {
        this.buyerId = buyerId;
        this.status = ListingStatus.REQUESTED;
    }

    public void accept() {
        this.status = ListingStatus.ACCEPTED;
    }

    public void complete() {
        this.status = ListingStatus.COMPLETED;
    }
    
    public void cancel() {
        this.status = ListingStatus.CANCELLED;
    }
}
