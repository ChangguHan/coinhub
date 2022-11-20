package com.jango.coinhub.service;

import com.jango.coinhub.dto.CoinBuyDTO;
import com.jango.coinhub.dto.CoinSellDTO;

import java.util.List;

public interface MarketService {
    double getCoinCurrentPrice(String coin);

    List<String> getCoins();

    CoinBuyDTO calculateBuy(List<String> commonCoins, double amount);
    CoinSellDTO calculateSell(CoinBuyDTO buyDTO);
}
