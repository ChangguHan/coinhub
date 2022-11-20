package com.jango.coinhub.service;

import com.jango.coinhub.dto.CoinBuyDTO;
import com.jango.coinhub.dto.CoinSellDTO;
import com.jango.coinhub.dto.TransferCalculateDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TransferCalculateService {
    private final CommonMarketService commonMarketService;
    private final Map<String, MarketService> marketServices;

    public TransferCalculateDTO calculate(String fromMarket, String toMarket, double amount) {

        // from, to : common coin
        List<String> commonCoins = commonMarketService.getCommonCoin(fromMarket, toMarket);

        MarketService fromMarketService = CommonMarketService.getMarketService(marketServices, fromMarket);
        MarketService toMarketService = CommonMarketService.getMarketService(marketServices, toMarket);

        // from 얼마에 살수있는지
        CoinBuyDTO fromMarketBuyDto = fromMarketService.calculateBuy(commonCoins, amount);
        // from 이체 수수료
        Map<String, Double> fromMarketTransferFee = fromMarketService.calculateFee(commonCoins, amount);
        // to 얼마에 팔수 있는지
        CoinSellDTO toMarketSellDto = toMarketService.calculateSell(commonCoins, amount);


        // 가장 높은 값을 받을수있는 코인을 선택
        String transferCoin = toMarketSellDto.getAmounts().keySet().get(0); // TODO: 가장 많은 현금 코인 선택

        return new TransferCalculateDTO(
                transferCoin,
                toMarketSellDto.getAmounts().get(transferCoin),
                fromMarketBuyDto.getOrderBooks().get(transferCoin),
                toMarketSellDto.getOrderBooks().get(transferCoin)
        );
    }
}
