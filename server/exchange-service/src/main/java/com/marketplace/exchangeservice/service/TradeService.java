package com.marketplace.exchangeservice.service;

import com.marketplace.common.exception.BusinessException;
import com.marketplace.common.exception.GlobalErrorCode;
import com.marketplace.exchangeservice.domain.Item;
import com.marketplace.exchangeservice.domain.Trade;
import com.marketplace.exchangeservice.domain.TradeStatus;
import com.marketplace.exchangeservice.dto.TradeDto;
import com.marketplace.exchangeservice.domain.TransactionRecord;
import com.marketplace.exchangeservice.repository.TradeRepository;
import com.marketplace.exchangeservice.repository.TransactionRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TradeService {

    private final TradeRepository tradeRepository;
    private final TransactionRecordRepository transactionRecordRepository;

    @Transactional
    public String registerTrade(TradeDto.RegisterRequest request) {
        Item item = Item.builder()
                .name(request.getItemName())
                .category(request.getCategory())
                .rarity(request.getRarity())
                .options(request.getOptions())
                .build();

        Trade trade = Trade.builder()
                .sellerId(request.getSellerId())
                .item(item)
                .price(request.getPrice())
                .status(TradeStatus.ON_SALE)
                .build();

        return tradeRepository.save(trade).getId();
    }

    @Transactional(readOnly = true)
    public List<TradeDto.Response> getAllTrades() {
        return tradeRepository.findAllByStatus(TradeStatus.ON_SALE).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public void requestTrade(String tradeId, Long buyerId) {
        Trade trade = tradeRepository.findById(tradeId)
                .orElseThrow(() -> new BusinessException(GlobalErrorCode.NOT_FOUND, "Trade not found"));

        if (trade.getStatus() != TradeStatus.ON_SALE) {
            throw new BusinessException(GlobalErrorCode.INVALID_INPUT, "Item is not on sale");
        }

        if (trade.getSellerId().equals(buyerId)) {
            throw new BusinessException(GlobalErrorCode.INVALID_INPUT, "Cannot buy your own item");
        }

        trade.request(buyerId);
        tradeRepository.save(trade);
    }

    @Transactional
    public void acceptTrade(String tradeId, Long sellerId) {
        Trade trade = tradeRepository.findById(tradeId)
                .orElseThrow(() -> new BusinessException(GlobalErrorCode.NOT_FOUND, "Trade not found"));

        if (!trade.getSellerId().equals(sellerId)) {
            throw new BusinessException(GlobalErrorCode.FORBIDDEN, "Not authorized");
        }

        if (trade.getStatus() != TradeStatus.REQUESTED) {
            throw new BusinessException(GlobalErrorCode.INVALID_INPUT, "Trade is not requested");
        }

        trade.accept();
        tradeRepository.save(trade);
        
        // TODO: Create Chat Room via Kafka or Feign
    }

    @Transactional
    public void completeTrade(String tradeId) {
        Trade trade = tradeRepository.findById(tradeId)
                .orElseThrow(() -> new BusinessException(GlobalErrorCode.NOT_FOUND, "Trade not found"));

        if (trade.getStatus() != TradeStatus.ACCEPTED) {
            throw new BusinessException(GlobalErrorCode.INVALID_INPUT, "Trade is not accepted");
        }

        trade.complete();
        tradeRepository.save(trade);

        TransactionRecord record = TransactionRecord.builder()
                .tradeId(trade.getId())
                .sellerId(trade.getSellerId())
                .buyerId(trade.getBuyerId())
                .price(trade.getPrice())
                .currencyType("GOLD") // Default for now
                .completedAt(java.time.LocalDateTime.now())
                .build();
        
        transactionRecordRepository.save(record);
    }

    private TradeDto.Response mapToResponse(Trade trade) {
        return TradeDto.Response.builder()
                .id(trade.getId())
                .sellerId(trade.getSellerId())
                .buyerId(trade.getBuyerId())
                .itemName(trade.getItem().getName())
                .options(trade.getItem().getOptions())
                .price(trade.getPrice())
                .status(trade.getStatus())
                .createdAt(trade.getCreatedAt())
                .build();
    }
}
