package com.marketplace.exchangeservice.controller;

import com.marketplace.common.dto.ApiResponse;
import com.marketplace.exchangeservice.domain.Trade;
import com.marketplace.exchangeservice.domain.TransactionRecord;
import com.marketplace.exchangeservice.repository.TradeRepository;
import com.marketplace.exchangeservice.repository.TransactionRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final TradeRepository tradeRepository;
    private final TransactionRecordRepository transactionRecordRepository;

    @GetMapping("/trades")
    public ApiResponse<List<Trade>> getAllTrades() {
        return ApiResponse.success(tradeRepository.findAll());
    }

    @GetMapping("/stats")
    public ApiResponse<List<TransactionRecord>> getTransactionStats() {
        return ApiResponse.success(transactionRecordRepository.findAll());
    }
}
