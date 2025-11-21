package com.marketplace.currencyservice.service;

import com.marketplace.common.exception.BusinessException;
import com.marketplace.common.exception.GlobalErrorCode;
import com.marketplace.currencyservice.domain.CurrencyListing;
import com.marketplace.currencyservice.domain.CurrencyTransaction;
import com.marketplace.currencyservice.domain.ListingStatus;
import com.marketplace.currencyservice.dto.CurrencyDto;
import com.marketplace.currencyservice.repository.CurrencyListingRepository;
import com.marketplace.currencyservice.repository.CurrencyTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CurrencyService {

    private final CurrencyListingRepository listingRepository;
    private final CurrencyTransactionRepository transactionRepository;

    @Transactional
    public Long createListing(CurrencyDto.CreateListingRequest request) {
        CurrencyListing listing = CurrencyListing.builder()
                .sellerId(request.getSellerId())
                .currencyAmount(request.getAmount())
                .price(request.getPrice())
                .status(ListingStatus.ON_SALE)
                .build();

        return listingRepository.save(listing).getId();
    }

    @Transactional(readOnly = true)
    public List<CurrencyDto.ListingResponse> getAvailableListings() {
        return listingRepository.findAllByStatus(ListingStatus.ON_SALE).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CurrencyDto.ListingResponse getListingDetail(Long id) {
        CurrencyListing listing = listingRepository.findById(id)
                .orElseThrow(() -> new BusinessException(GlobalErrorCode.NOT_FOUND, "Listing not found"));
        return mapToResponse(listing);
    }

    @Transactional
    public void requestListing(Long listingId, Long buyerId) {
        CurrencyListing listing = listingRepository.findById(listingId)
                .orElseThrow(() -> new BusinessException(GlobalErrorCode.NOT_FOUND, "Listing not found"));

        if (listing.getStatus() != ListingStatus.ON_SALE) {
            throw new BusinessException(GlobalErrorCode.INVALID_INPUT, "Listing is not on sale");
        }

        if (listing.getSellerId().equals(buyerId)) {
            throw new BusinessException(GlobalErrorCode.INVALID_INPUT, "Cannot buy your own listing");
        }

        listing.request(buyerId);
        listingRepository.save(listing);
    }

    @Transactional
    public void acceptListing(Long listingId, Long sellerId) {
        CurrencyListing listing = listingRepository.findById(listingId)
                .orElseThrow(() -> new BusinessException(GlobalErrorCode.NOT_FOUND, "Listing not found"));

        if (!listing.getSellerId().equals(sellerId)) {
            throw new BusinessException(GlobalErrorCode.FORBIDDEN, "Not authorized");
        }

        if (listing.getStatus() != ListingStatus.REQUESTED) {
            throw new BusinessException(GlobalErrorCode.INVALID_INPUT, "Listing is not requested");
        }

        listing.accept();
        listingRepository.save(listing);
    }

    @Transactional
    public void completeListing(Long listingId) {
        CurrencyListing listing = listingRepository.findById(listingId)
                .orElseThrow(() -> new BusinessException(GlobalErrorCode.NOT_FOUND, "Listing not found"));

        if (listing.getStatus() != ListingStatus.ACCEPTED) {
            throw new BusinessException(GlobalErrorCode.INVALID_INPUT, "Listing is not accepted");
        }

        listing.complete();
        listingRepository.save(listing);

        CurrencyTransaction transaction = CurrencyTransaction.builder()
                .listingId(listing.getId())
                .sellerId(listing.getSellerId())
                .buyerId(listing.getBuyerId())
                .amount(listing.getCurrencyAmount())
                .price(listing.getPrice())
                .build();

        transactionRepository.save(transaction);
    }

    private CurrencyDto.ListingResponse mapToResponse(CurrencyListing listing) {
        return CurrencyDto.ListingResponse.builder()
                .id(listing.getId())
                .sellerId(listing.getSellerId())
                .amount(listing.getCurrencyAmount())
                .price(listing.getPrice())
                .status(listing.getStatus())
                .createdAt(listing.getCreatedAt())
                .build();
    }
}
