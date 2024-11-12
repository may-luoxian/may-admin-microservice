package com.may.utils.feignapi;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("auth")
public interface AuthResourceClient {
    @GetMapping("/clearResource")
    void clearResource();
}
