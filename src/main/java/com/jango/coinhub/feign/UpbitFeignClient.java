package com.jango.coinhub.feign;

import com.jango.coinhub.model.UpbitCoinPrice;
import com.jango.coinhub.model.UpbitMarketCode;
import com.jango.coinhub.model.UpbitOrderBooks;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "upbit", url = "https://api.upbit.com/v1")
public interface UpbitFeignClient {
    @Cacheable("UPBIT_COIN_PRICE")
    @GetMapping("/ticker")
    List<UpbitCoinPrice> getCoinPrice(@RequestParam("markets") String coin);

    @Cacheable("UPBIT_MARKET_CODE")
    @GetMapping("/market/all")
    List<UpbitMarketCode> getMarketCode();

    @Cacheable("UPBIT_ORDER_BOOKS")
    @GetMapping("/orderbook")
    List<UpbitOrderBooks> getOrderBooks(@RequestParam List<String> markets);
}
