package com.jango.coinhub.service.bithumb;

import com.jango.coinhub.service.BithumbMarketService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;

@Disabled
@EnableFeignClients
@SpringBootTest
class BithumbMarketServiceIntegerationTest {
    @Autowired
    private BithumbMarketService bithumbMarketService;

    @Test
    void calculateFeeTest() throws Exception {
        Map<String, Double> result = bithumbMarketService.calculateFee();
        assertFalse(result.isEmpty());
    }
}