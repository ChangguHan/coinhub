package com.jango.coinhub.feign;

import com.jango.coinhub.feign.response.BithumbResponse;
import com.jango.coinhub.model.BithumbAssetEachStatus;
import com.jango.coinhub.model.BithumbCoinPrice;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@FeignClient(name = "bithumb", url = "https://api.bithumb.com/public")
public interface BithumbFeignClient {
    @Cacheable("BITHUMB_COIN_PRICE")
    @GetMapping("/ticker/{coin}")
    BithumbResponse<BithumbCoinPrice> getCoinPrice(@PathVariable("coin") String coin);

    @Cacheable("BITHUMB_ASSET_STATUS")
    @GetMapping("/assetsstatus/ALL")
    BithumbResponse<Map<String, BithumbAssetEachStatus>> getAssetStatus();

    @Cacheable("BITHUMB_ORDER_BOOK")
    @GetMapping("/orderbook/ALL_KRW")
    BithumbResponse<Map<String, Object>> getOrderBook();
}
