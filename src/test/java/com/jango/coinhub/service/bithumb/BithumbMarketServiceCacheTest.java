package com.jango.coinhub.service.bithumb;

import com.jango.coinhub.feign.BithumbFeignClient;
import com.jango.coinhub.service.BithumbMarketService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.SimpleKey;
import org.springframework.cloud.openfeign.EnableFeignClients;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@Disabled
@EnableCaching
@EnableFeignClients
@SpringBootTest
class BithumbMarketServiceCacheTest {
    @Autowired
    private BithumbFeignClient bithumbFeignClient;

    @Autowired
    private BithumbMarketService bithumbMarketService;

    @Autowired
    private CacheManager cacheManager;

    @Test
    void getCoinPriceTest() {
        String parameter = "BTC";
        assertNull(cacheManager.getCache("BITHUMB_COIN_PRICE").get(parameter));

        bithumbFeignClient.getCoinPrice(parameter);

        assertNotNull(cacheManager.getCache("BITHUMB_COIN_PRICE").get(parameter));
    }

    @Test
    void getAssetStatusCacheTest() {
        assertNull(cacheManager.getCache("BITHUMB_ASSET_STATUS").get(SimpleKey.EMPTY));

        bithumbFeignClient.getAssetStatus();

        assertNotNull(cacheManager.getCache("BITHUMB_ASSET_STATUS").get(SimpleKey.EMPTY));
    }

    @Test
    void getOrderBookCacheTest() {
        assertNull(cacheManager.getCache("BITHUMB_ORDER_BOOK").get(SimpleKey.EMPTY));

        bithumbFeignClient.getOrderBook();

        assertNotNull(cacheManager.getCache("BITHUMB_ORDER_BOOK").get(SimpleKey.EMPTY));
    }

    @Test
    void calculateFeeTest() throws Exception {
        assertNull(cacheManager.getCache("BITHUMB_CALCULATE_FEE").get(SimpleKey.EMPTY));

        bithumbMarketService.calculateFee();

        assertNotNull(cacheManager.getCache("BITHUMB_CALCULATE_FEE").get(SimpleKey.EMPTY));
    }
}