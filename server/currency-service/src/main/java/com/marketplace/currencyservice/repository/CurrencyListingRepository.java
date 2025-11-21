package com.marketplace.currencyservice.repository;

import com.marketplace.currencyservice.domain.CurrencyListing;
import com.marketplace.currencyservice.domain.ListingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CurrencyListingRepository extends JpaRepository<CurrencyListing, Long> {
    List<CurrencyListing> findAllByStatus(ListingStatus status);
}
