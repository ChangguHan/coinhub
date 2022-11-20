package com.jango.coinhub.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor
public class TransferCalculateDTO {
    private String coin;
    private double buyAmount; // 산 코인의 양
    private double fee; // 이체 수수료
    private double sellAmount; // 판 현금의 양
    private Map<Double, Double> buyOrderBook;
    private Map<Double, Double> sellOrderBook;
}
