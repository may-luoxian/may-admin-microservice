package com.may.gateway.client;

import com.may.utils.model.dto.ValidResourceDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

@ReactiveFeignClient(name = "auth")
public interface AuthClient {
    @PostMapping("/validateResource")
    Mono<String> validateResources(@RequestBody ValidResourceDTO validResourceDTO, @RequestHeader("Authorization") String token);
}
