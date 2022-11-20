package com.jango.coinhub.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommonMarketServiceTest {
    @Mock
    private BithumbMarketService bithumbMarketService;

    @Mock
    private UpbitMarketService upbitMarketService;

    private CommonMarketService commonMarketService;

    @BeforeEach
    void setUp() {
        commonMarketService = new CommonMarketService(
                Map.of("bithumbMarketService", bithumbMarketService,
                        "upbitMarketService", upbitMarketService)
        );
    }

    @Test
    void getPriceTest() {
        // given
        double testAmount = 123.456;
        String testCoin = "testCoin";
        when(bithumbMarketService.getCoinCurrentPrice(testCoin)).thenReturn(testAmount);
        when(upbitMarketService.getCoinCurrentPrice(testCoin)).thenReturn(testAmount);

        // when & then
        assertEquals(testAmount, commonMarketService.getPrice("bithumb", testCoin));
        assertEquals(testAmount, commonMarketService.getPrice("upbit", testCoin));
    }

    @Test
    void getMarketServiceTest() {
        // given
        Map<String, MarketService> marketServices = new HashMap<>();
        marketServices.put("bithumbMarketService", bithumbMarketService);
        marketServices.put("upbitMarketService", upbitMarketService);

        // when & then
        assertEquals(bithumbMarketService, CommonMarketService.getMarketService(marketServices, "bithumb"));
        assertEquals(bithumbMarketService, CommonMarketService.getMarketService(marketServices, "Bithumb"));
        assertEquals(bithumbMarketService, CommonMarketService.getMarketService(marketServices, "BITHUMB"));
        assertEquals(bithumbMarketService, CommonMarketService.getMarketService(marketServices, "bithuMB"));
        assertEquals(upbitMarketService, CommonMarketService.getMarketService(marketServices, "upbit"));
        assertEquals(upbitMarketService, CommonMarketService.getMarketService(marketServices, "Upbit"));
        assertEquals(upbitMarketService, CommonMarketService.getMarketService(marketServices, "UPBIT"));
        assertEquals(upbitMarketService, CommonMarketService.getMarketService(marketServices, "upbIT"));
    }

}









