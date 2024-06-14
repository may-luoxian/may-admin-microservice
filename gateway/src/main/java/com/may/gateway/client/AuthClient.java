package com.may.gateway.client;

import org.springframework.web.bind.annotation.PostMapping;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

@ReactiveFeignClient(name = "auth")
public interface AuthClient {
    @PostMapping("/validateResource")
    Mono<String> validateResources();
}
