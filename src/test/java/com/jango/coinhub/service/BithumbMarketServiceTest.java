package com.jango.coinhub.service;

import com.jango.coinhub.dto.CoinBuyDTO;
import com.jango.coinhub.dto.CoinSellDTO;
import com.jango.coinhub.feign.BithumbFeignClient;
import com.jango.coinhub.feign.response.BithumbResponse;
import com.jango.coinhub.model.BithumbAssetEachStatus;
import com.jango.coinhub.model.BithumbCoinPrice;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
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

    @Test
    void calculateBuyTest() {
        // given
        List<String> commonCoin = List.of("A", "B");
        BithumbResponse<Map<String, Object>> mockOrderBook = mockBithumbOrderBook();
        when(bithumbFeignClient.getOrderBook()).thenReturn(mockOrderBook);

        // when
        CoinBuyDTO result = bithumbMarketService.calculateBuy(commonCoin, 5);

        // then
        assertEquals(1+1+0.5, result.getAmounts().get("A"));
        assertEquals(1, result.getOrderBooks().get("A").get(1D));
        assertEquals(1, result.getOrderBooks().get("A").get(2D));
        assertEquals(0.5, result.getOrderBooks().get("A").get(4D));

        assertEquals(2+1.5, result.getAmounts().get("B"));
        assertEquals(2, result.getOrderBooks().get("B").get(1D));
        assertEquals(1.5, result.getOrderBooks().get("B").get(2D));

        assertEquals(3+1, result.getAmounts().get("C"));
        assertEquals(3, result.getOrderBooks().get("C").get(1D));
        assertEquals(1, result.getOrderBooks().get("C").get(2D));
    }

    @Test
    void calculateSellTest() {
        // given
        Map<String, Double> amounts = Map.of("A", 2.5, "B", 3D, "C", 123D);
        BithumbResponse<Map<String, Object>> mockOrderBook = mockBithumbOrderBook();
        when(bithumbFeignClient.getOrderBook()).thenReturn(mockOrderBook);

        // when
        CoinSellDTO result = bithumbMarketService.calculateSell(new CoinBuyDTO(amounts, null));

        // then
        assertEquals(4*1 + 2*1 + 1*0.5, result.getAmounts().get("A"));
        assertEquals(1, result.getOrderBooks().get("A").get(4D));
        assertEquals(1, result.getOrderBooks().get("A").get(2D));
        assertEquals(0.5, result.getOrderBooks().get("A").get(1D));

        assertEquals(4*2 + 2*1, result.getAmounts().get("B"));
        assertEquals(2, result.getOrderBooks().get("B").get(4D));
        assertEquals(1, result.getOrderBooks().get("B").get(2D));

        assertNull(result.getAmounts().get("C"));
        assertNull(result.getOrderBooks().get("C"));
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

    private BithumbResponse<Map<String, Object>> mockBithumbOrderBook() {
        BithumbResponse<Map<String, Object>> result = new BithumbResponse<>();
        result.setData(
                Map.of(
                        "A", Map.of(
                                "bids", new ArrayList<>(List.of( // wanna Buy
                                        Map.of("price", "1","quantity","1"),
                                        Map.of("price", "2","quantity","1"),
                                        Map.of("price", "4","quantity","1")
                                )),
                                "asks", new ArrayList<>(List.of( // wanna Sell
                                        Map.of("price", "4","quantity","1"), // 2
                                        Map.of("price", "2","quantity","1"), // 2
                                        Map.of("price", "1","quantity","1") // 1
                                ))
                        ),
                        "B", Map.of(
                                "bids", new ArrayList<>(List.of( // wanna Buy
                                        Map.of("price", "1","quantity","2"),
                                        Map.of("price", "2","quantity","2"),
                                        Map.of("price", "4","quantity","2")
                                        )),
                                "asks", new ArrayList<>(List.of( // wanna Sell
                                        Map.of("price", "4","quantity","2"),
                                        Map.of("price", "2","quantity","2"), // 1.5
                                        Map.of("price", "1","quantity","2") // 2
                                ))
                        ),
                        "C", Map.of(
                                "bids", new ArrayList<>(List.of( // wanna Buy
                                        Map.of("price", "1","quantity","3"),
                                        Map.of("price", "2","quantity","3"),
                                        Map.of("price", "4","quantity","3")
                                )),
                                "asks", new ArrayList<>(List.of( // wanna Sell
                                        Map.of("price", "4","quantity","3"),
                                        Map.of("price", "2","quantity","3"), // 1
                                        Map.of("price", "1","quantity","3") // 3
                                ))
                        )
                )
        );

        return result;
    }
}