package com.marketplace.currencyservice.controller;

import com.marketplace.common.dto.ApiResponse;
import com.marketplace.currencyservice.dto.CurrencyDto;
import com.marketplace.currencyservice.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/currency")
@RequiredArgsConstructor
public class CurrencyController {

    private final CurrencyService currencyService;

    @PostMapping("/listings")
    public ApiResponse<Long> createListing(@RequestBody CurrencyDto.CreateListingRequest request) {
        return ApiResponse.success(currencyService.createListing(request));
    }

    @GetMapping("/listings")
    public ApiResponse<List<CurrencyDto.ListingResponse>> getAvailableListings() {
        return ApiResponse.success(currencyService.getAvailableListings());
    }

    @GetMapping("/listings/{id}")
    public ApiResponse<CurrencyDto.ListingResponse> getListingDetail(@PathVariable Long id) {
        return ApiResponse.success(currencyService.getListingDetail(id));
    }

    @PostMapping("/listings/{id}/request")
    public ApiResponse<Void> requestListing(@PathVariable Long id, @RequestParam Long buyerId) {
        currencyService.requestListing(id, buyerId);
        return ApiResponse.success(null);
    }

    @PostMapping("/listings/{id}/accept")
    public ApiResponse<Void> acceptListing(@PathVariable Long id, @RequestParam Long sellerId) {
        currencyService.acceptListing(id, sellerId);
        return ApiResponse.success(null);
    }

    @PostMapping("/listings/{id}/complete")
    public ApiResponse<Void> completeListing(@PathVariable Long id) {
        currencyService.completeListing(id);
        return ApiResponse.success(null);
    }
}
