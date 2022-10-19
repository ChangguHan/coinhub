package com.jango.coinhub.controller;

import com.jango.coinhub.service.MarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MarketController {
    @Autowired
    private MarketService marketService;

    @GetMapping("/price") // 코인의 최근 가격 : 어떤 마켓, 어떤 코인인지
    public double getPrice(
            @RequestParam String market, @RequestParam String coin
    ) {
        return marketService.getPrice(market, coin);
    }

}
