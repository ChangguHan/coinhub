package com.jango.coinhub.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import com.jango.coinhub.dto.CoinBuyDTO;
import com.jango.coinhub.dto.CoinSellDTO;
import com.jango.coinhub.dto.TransferCalculateDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TransferCalculateServiceTest {
    @Mock
    private CommonMarketService commonMarketService;

    @Mock
    private Map<String, MarketService> marketServices;

    @Mock
    private MarketService fromMarketService;

    @Mock
    private MarketService toMarketService;

    private TransferCalculateService transferCalculateService;

    @BeforeEach
    void setUp() {
        transferCalculateService = new TransferCalculateService(
                commonMarketService, Map.of("fromMarketService", fromMarketService,
                "toMarketService", toMarketService)
        );
    }

    @Test
    void calculateTest() throws Exception{
        // given
        String fromMarketName = "from";
        String toMarketName = "to";
        List<String> commonCoins = List.of("A", "B");
        CoinBuyDTO coinBuyDto = mockCoinBuyDto();
        CoinSellDTO coinSellDto = mockCoinSellDto();

        Map<String, Double> fromMarketTransferFee = Map.of("A",0.1D, "B", 0.01D);
        when(commonMarketService.getCommonCoin(fromMarketName, toMarketName)).thenReturn(commonCoins);
        when(fromMarketService.calculateBuy(commonCoins, 5D)).thenReturn(coinBuyDto);
        when(fromMarketService.calculateFee()).thenReturn(fromMarketTransferFee);
        when(toMarketService.calculateSell(Map.of("A", 2.4, "B", 3.49))).thenReturn(coinSellDto);

        // when
        List<TransferCalculateDTO> result = transferCalculateService.calculate("from", "to", 5D);

        // then
        assertEquals(commonCoins.size(), result.size());

        assertEquals("B", result.get(0).getCoin());
        assertEquals(coinBuyDto.getAmounts().get("B"), result.get(0).getBuyAmount());
        assertEquals(fromMarketTransferFee.get("B"), result.get(0).getFee());
        assertEquals(coinSellDto.getAmounts().get("B"), result.get(0).getSellAmount());
        assertEquals(coinBuyDto.getOrderBooks().get("B"), result.get(0).getBuyOrderBook());
        assertEquals(coinSellDto.getOrderBooks().get("B"), result.get(0).getSellOrderBook());

        assertEquals("A", result.get(1).getCoin());
        assertEquals(coinBuyDto.getAmounts().get("A"), result.get(1).getBuyAmount());
        assertEquals(fromMarketTransferFee.get("A"), result.get(1).getFee());
        assertEquals(coinSellDto.getAmounts().get("A"), result.get(1).getSellAmount());
        assertEquals(coinBuyDto.getOrderBooks().get("A"), result.get(1).getBuyOrderBook());
        assertEquals(coinSellDto.getOrderBooks().get("A"), result.get(1).getSellOrderBook());
    }

    private CoinBuyDTO mockCoinBuyDto() {
        return new CoinBuyDTO(
                Map.of(
                        "A", 1+1+0.5,
                        "B", 2+1.5
                ),
                Map.of(
                        "A", new TreeMap<>(Map.of(1D, 1D, 2D, 1D, 4D, 0.5)),
                        "B",new TreeMap<>(Map.of(1D, 2D, 2D, 1.5))
                ));
    }

    private CoinSellDTO mockCoinSellDto() {
        // A: 1+1+0.5 - 0.1 = 2.4
        // B: 2+1.5 - 0.01 = 3.49

        SortedMap<Double, Double> orderBooksForA = new TreeMap<>(Collections.reverseOrder());
        orderBooksForA.put(4D, 1D);
        orderBooksForA.put(2D, 1D);
        orderBooksForA.put(1D, 0.4);

        SortedMap<Double, Double> orderBooksForB = new TreeMap<>(Collections.reverseOrder());
        orderBooksForB.put(4D, 2D);
        orderBooksForB.put(2D, 1.49);

        return new CoinSellDTO(
                Map.of(
                        "A", 1*4 + 1*2 +0.4*1,
                        "B", 2*4 +1.49*2
                ),
                Map.of(
                        "A", orderBooksForA,
                        "B", orderBooksForB
                )
        );

    }
}