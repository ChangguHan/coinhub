package com.jango.coinhub.controller;

import com.jango.coinhub.service.TransferCalculateService;
import com.jango.coinhub.view.TransferCalculateResponseView;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TransferCalculateController {
    private final TransferCalculateService transferCalculateService;

    @GetMapping("/transfer-calculate")
    public List<TransferCalculateResponseView> transferCalculate (
            @RequestParam String fromMarket,
            @RequestParam String toMarket,
            @RequestParam double amount
    ) throws Exception
    {
        return transferCalculateService.calculate(fromMarket, toMarket, amount)
                .stream()
                .map(k -> TransferCalculateResponseView.of(k, amount)).toList();
    }
}
