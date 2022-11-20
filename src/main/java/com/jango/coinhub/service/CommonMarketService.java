package com.jango.coinhub.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CommonMarketService {
    private final Map<String, MarketService> marketServices;

    public double getPrice(String market, String coin) {
        MarketService marketService = getMarketService(marketServices, market);

        return marketService.getCoinCurrentPrice(coin);
    }

    public static MarketService getMarketService(Map<String, MarketService> marketServices, String market) {
        for(String key : marketServices.keySet()) {
            if(key.substring(0, market.length()).equals(market.toLowerCase())) {
                return marketServices.get(key);
            }
        }
        return null;
    }
}