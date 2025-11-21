package com.marketplace.exchangeservice.controller;

import com.marketplace.common.dto.ApiResponse;
import com.marketplace.exchangeservice.dto.TradeDto;
import com.marketplace.exchangeservice.service.TradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/trades")
@RequiredArgsConstructor
public class TradeController {

    private final TradeService tradeService;

    @PostMapping
    public ApiResponse<String> registerTrade(@RequestBody TradeDto.RegisterRequest request) {
        return ApiResponse.success(tradeService.registerTrade(request));
    }

    @GetMapping
    public ApiResponse<List<TradeDto.Response>> getAllTrades() {
        return ApiResponse.success(tradeService.getAllTrades());
    }

    @PostMapping("/{id}/request")
    public ApiResponse<Void> requestTrade(@PathVariable String id, @RequestParam Long buyerId) {
        tradeService.requestTrade(id, buyerId);
        return ApiResponse.success(null);
    }

    @PostMapping("/{id}/accept")
    public ApiResponse<Void> acceptTrade(@PathVariable String id, @RequestParam Long sellerId) {
        tradeService.acceptTrade(id, sellerId);
        return ApiResponse.success(null);
    }

    @PostMapping("/{id}/complete")
    public ApiResponse<Void> completeTrade(@PathVariable String id) {
        tradeService.completeTrade(id);
        return ApiResponse.success(null);
    }
}
