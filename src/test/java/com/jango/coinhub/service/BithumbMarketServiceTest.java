package com.jango.coinhub.service;

import com.jango.coinhub.feign.BithumbFeignClient;
import com.jango.coinhub.feign.response.BithumbResponse;
import com.jango.coinhub.model.BithumbAssetEachStatus;
import com.jango.coinhub.model.BithumbCoinPrice;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BithumbMarketServiceTest {
    @Mock
    private BithumbFeignClient bithumbFeignClient;

    @InjectMocks
    private BithumbMarketService bithumbMarketService;

    @Test
    void getCoinCurrentPriceTest() {
        // given
        String mockCoin = "testCoin";
        String mockPrice = "123.456";
        BithumbCoinPrice mockBithumbCoinPrice = new BithumbCoinPrice();
        mockBithumbCoinPrice.setClosing_price(mockPrice);

        // when
        when(bithumbFeignClient.getCoinPrice(mockCoin.toUpperCase()+"_KRW")).thenReturn(mockBithumbCoinPrice(mockPrice));

        // then
        assertEquals(Double.parseDouble(mockPrice), bithumbMarketService.getCoinCurrentPrice(mockCoin));
    }

    @Test
    void getCoinsTest() {
        // given
        String mockCoin1 = "coin1";
        String mockCoin2 = "coin2";
        String mockCoin3 = "coin3";

        // when
        when(bithumbFeignClient.getAssetStatus()).thenReturn(mockBithumbAssetStatus(mockCoin1, mockCoin2, mockCoin3));

        // then
        List<String> result = bithumbMarketService.getCoins();
        assertTrue(result.contains(mockCoin1.toUpperCase()));
        assertTrue(result.contains(mockCoin2.toUpperCase()));
        assertTrue(result.contains(mockCoin3.toUpperCase()));
        assertEquals(3, result.size());
    }

    private BithumbResponse<BithumbCoinPrice> mockBithumbCoinPrice(String price) {
        BithumbResponse response = new BithumbResponse();
        BithumbCoinPrice data = new BithumbCoinPrice();
        data.setClosing_price(price);
        response.setData(data);
        return response;
    }

    private BithumbResponse<Map<String, BithumbAssetEachStatus>> mockBithumbAssetStatus(String coin1, String coin2, String coin3) {
        BithumbResponse response = new BithumbResponse();
        Map<String, BithumbAssetEachStatus> data = new HashMap<>();
        data.put(coin1, new BithumbAssetEachStatus(1, 1));
        data.put(coin2, new BithumbAssetEachStatus(1, 1));
        data.put(coin3, new BithumbAssetEachStatus(1, 1));
        response.setData(data);
        return response;
    }
}