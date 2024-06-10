package com.may.client;

import org.springframework.web.bind.annotation.GetMapping;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

@ReactiveFeignClient(name = "auth")
public interface SysLogClient {
    @GetMapping("/hello")
    Mono<String> getSysLogListWithPage();
}
