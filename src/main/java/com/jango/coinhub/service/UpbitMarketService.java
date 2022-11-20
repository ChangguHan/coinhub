package com.jango.coinhub.service;

import com.jango.coinhub.feign.UpbitFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UpbitMarketService implements MarketService {
    private final UpbitFeignClient upbitFeignClient;

    public double getCoinCurrentPrice(String coin) {
        return upbitFeignClient.getCoinPrice("KRW-" + coin.toUpperCase())
                .get(0)
                .getTrade_price();
    }

    public List<String> getCoins() {
        List<String> result = new ArrayList<>();
        upbitFeignClient.getMarketCode().forEach(k -> {
            if(k.getMarket().startsWith("KRW")) {
                result.add(k.getMarket().substring(4).toUpperCase());
            }
        });
        return result;
    }
}
