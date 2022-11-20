package com.jango.coinhub.feign;

import com.jango.coinhub.feign.response.BithumbResponse;
import com.jango.coinhub.model.BithumbAssetEachStatus;
import com.jango.coinhub.model.BithumbCoinPrice;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@FeignClient(name = "bithumb", url = "https://api.bithumb.com/public")
public interface BithumbFeignClient {
    @GetMapping("/ticker/{coin}")
    BithumbResponse<BithumbCoinPrice> getCoinPrice(@PathVariable("coin") String coin);

    @GetMapping("/assetsstatus/ALL")
    BithumbResponse<Map<String, BithumbAssetEachStatus>> getAssetStatus();

    @GetMapping("/orderbook/ALL_KRW")
    BithumbResponse<Map<String, Object>> getOrderBook();
}
