package com.jango.coinhub.service.upbit;

import com.jango.coinhub.feign.UpbitFeeFeignClient;
import com.jango.coinhub.feign.UpbitFeignClient;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.SimpleKey;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@Disabled
@EnableFeignClients
@SpringBootTest
class UpbitMarketServiceCacheTest {
    @Autowired
    private UpbitFeeFeignClient upbitFeeFeignClient;
    @Autowired
    private UpbitFeignClient upbitFeignClient;
    @Autowired
    private CacheManager cacheManager;

    @Test
    void getWithdrawalFeeCacheTest() {
        assertNull(cacheManager.getCache("UPBIT_WITHDRAWAL_FEE").get(SimpleKey.EMPTY));

        upbitFeeFeignClient.getWithdrawalFee();

        assertNotNull(cacheManager.getCache("UPBIT_WITHDRAWAL_FEE").get(SimpleKey.EMPTY));
    }

    @Test
    void getCoinPriceCacheTest() {
        String parameter = "KRW-BTC";
        assertNull(cacheManager.getCache("UPBIT_COIN_PRICE").get(parameter));

        upbitFeignClient.getCoinPrice(parameter);

        assertNotNull(cacheManager.getCache("UPBIT_COIN_PRICE").get(parameter));
    }

    @Test
    void getMarketCodeCacheTest() {
        assertNull(cacheManager.getCache("UPBIT_MARKET_CODE").get(SimpleKey.EMPTY));

        upbitFeignClient.getMarketCode();

        assertNotNull(cacheManager.getCache("UPBIT_MARKET_CODE").get(SimpleKey.EMPTY));
    }

    @Test
    void getOrderBooksCacheTest() {
        List<String> parameter = List.of("KRW-BTC");
        assertNull(cacheManager.getCache("UPBIT_ORDER_BOOKS").get(parameter));

        upbitFeignClient.getOrderBooks(parameter);

        assertNotNull(cacheManager.getCache("UPBIT_ORDER_BOOKS").get(parameter));
    }
}