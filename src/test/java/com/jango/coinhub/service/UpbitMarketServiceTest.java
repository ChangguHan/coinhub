package com.jango.coinhub.service;

import com.jango.coinhub.feign.UpbitFeignClient;
import com.jango.coinhub.model.UpbitCoinPrice;
import com.jango.coinhub.model.UpbitMarketCode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpbitMarketServiceTest {
    @Mock
    private UpbitFeignClient upbitFeignClient;

    @InjectMocks
    private UpbitMarketService upbitMarketService;

    @Test
    void getCoinCurrentPriceTest() {
        // given
        String mockCoin = "testCoin";
        double mockPrice = 123.456;
        UpbitCoinPrice mockUpbitCoinPrice = new UpbitCoinPrice();
        mockUpbitCoinPrice.setTrade_price(mockPrice);

        // when
        when(upbitFeignClient.getCoinPrice("KRW-TESTCOIN")).thenReturn(List.of(mockUpbitCoinPrice));

        // then
        assertEquals(mockPrice, upbitMarketService.getCoinCurrentPrice(mockCoin));
    }

    @Test
    void getCoinsTest() {
        // given
        String mockCoin1 = "coin1";
        String mockCoin2 = "coin2";
        String mockCoin3 = "coin3";
        UpbitMarketCode mockUpbitCoin1 = mockUpbitMarketCode(mockCoin1);
        UpbitMarketCode mockUpbitCoin2 = mockUpbitMarketCode(mockCoin2);
        UpbitMarketCode mockUpbitCoin3 = mockUpbitMarketCode(mockCoin3);

        // when
        when(upbitFeignClient.getMarketCode()).thenReturn(List.of(mockUpbitCoin1, mockUpbitCoin2, mockUpbitCoin3));

        // then
        List<String> result = upbitMarketService.getCoins();
        assertTrue(result.contains(mockCoin1.toUpperCase()));
        assertTrue(result.contains(mockCoin2.toUpperCase()));
        assertTrue(result.contains(mockCoin3.toUpperCase()));
        assertEquals(3, result.size());
    }

    private UpbitMarketCode mockUpbitMarketCode(String coin) {
        UpbitMarketCode result = new UpbitMarketCode();
        result.setMarket("KRW_" + coin);
        return result;
    }

}