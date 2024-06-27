package com.may.gateway.config;

import com.may.gateway.client.AuthClient;
import org.springframework.context.annotation.Configuration;
import reactivefeign.spring.config.EnableReactiveFeignClients;
//test
@Configuration(proxyBeanMethods = false)
@EnableReactiveFeignClients(clients = {AuthClient.class})
public class FeignClientConfig {
}
