package com.may.config;

import com.may.client.AuthClient;
import org.springframework.context.annotation.Configuration;
import reactivefeign.spring.config.EnableReactiveFeignClients;

@Configuration(proxyBeanMethods = false)
@EnableReactiveFeignClients(clients = {AuthClient.class})
public class FeignClientConfig {
}
