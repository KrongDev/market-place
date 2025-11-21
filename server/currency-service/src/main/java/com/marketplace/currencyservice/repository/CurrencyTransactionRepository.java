package com.marketplace.currencyservice.repository;

import com.marketplace.currencyservice.domain.CurrencyTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyTransactionRepository extends JpaRepository<CurrencyTransaction, Long> {
}
