package com.marketplace.exchangeservice.repository;

import com.marketplace.exchangeservice.domain.Trade;
import com.marketplace.exchangeservice.domain.TradeStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TradeRepository extends MongoRepository<Trade, String> {
    List<Trade> findAllByStatus(TradeStatus status);
    List<Trade> findAllBySellerId(Long sellerId);
}
