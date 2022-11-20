package com.jango.coinhub.controller;

import com.jango.coinhub.service.TransferCalculateService;
import com.jango.coinhub.view.TransferCalculateResponseView;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class TransferCalculateController {
    private final TransferCalculateService transferCalculateService;

    @GetMapping("/transfer-calculate")
    public TransferCalculateResponseView getPrice(
            @RequestParam String fromMarket,
            @RequestParam String toMarket,
            @RequestParam double amount
    ) {
        return new TransferCalculateResponseView("BTC", 123.45,
                Map.of(123D, 456D),
                Map.of(123D, 456D));
//        return TransferCalculateResponseView.of(
//                transferCalculateService.calculate(fromMarket, toMarket, amount)
//        );
    }
}
