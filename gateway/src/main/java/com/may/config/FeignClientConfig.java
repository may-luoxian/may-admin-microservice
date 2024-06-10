package com.may.config;

import com.may.client.SysLogClient;
import org.springframework.context.annotation.Configuration;
import reactivefeign.spring.config.EnableReactiveFeignClients;

@Configuration(proxyBeanMethods = false)
@EnableReactiveFeignClients(clients = {SysLogClient.class})
public class FeignClientConfig {
}
