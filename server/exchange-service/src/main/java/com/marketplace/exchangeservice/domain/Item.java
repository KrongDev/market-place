package com.marketplace.exchangeservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    private String name;
    private String category;
    private String rarity;
    private Map<String, Object> options;
}
