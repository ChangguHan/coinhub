package com.jango.coinhub.feign;

import com.jango.coinhub.constant.CacheConstants;
import com.jango.coinhub.model.UpbitWithdrawalFee;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "upbitFee", url = "https://api-manager.upbit.com/api/v1/kv")
public interface UpbitFeeFeignClient {
    @Cacheable(CacheConstants.UPBIT_WITHDRAWAL_FEE)
    @GetMapping("/UPBIT_PC_COIN_DEPOSIT_AND_WITHDRAW_GUIDE")
    UpbitWithdrawalFee getWithdrawalFee();
}
