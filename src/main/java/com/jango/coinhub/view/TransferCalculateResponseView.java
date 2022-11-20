package com.jango.coinhub.view;

import com.jango.coinhub.dto.TransferCalculateDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor
public class TransferCalculateResponseView {
    private String coin;
    private double amount;
    private Map<Double, Double> buyOrderBook;
    private Map<Double, Double> sellOrderBook;

    // DTO -> View
    public static TransferCalculateResponseView of(TransferCalculateDTO dto) {
        return new TransferCalculateResponseView(
                dto.getCoin(),
                dto.getAmount(),
                dto.getBuyOrderBook(),
                dto.getSellOrderBook()
        );
    }
}
