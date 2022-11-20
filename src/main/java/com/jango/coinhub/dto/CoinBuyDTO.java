package com.jango.coinhub.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;

@Getter
@AllArgsConstructor
public class CoinBuyDTO {
    private Map<String, Double> amounts;
    private Map<String, SortedMap<Double, Double>> orderBooks;

    public Map<String /*Coin Name*/ , Double/* Amount */> afterTransferFee(
            Map<String /*Coin Name*/ , Double/* Withdrawal Fee */> fromMarketTransferFee
    ) {
        Map<String, Double> result = new HashMap<>();

        amounts.forEach((k,v) -> {
            if(fromMarketTransferFee.containsKey(k)) {
                result.put(k, amounts.get(k) - fromMarketTransferFee.get(k));
            }
        });

        return result;
    }
}
