package com.marketplace.exchangeservice.repository;

import com.marketplace.exchangeservice.domain.TransactionRecord;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TransactionRecordRepository extends MongoRepository<TransactionRecord, String> {
}
