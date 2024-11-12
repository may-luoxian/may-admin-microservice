package com.may.gateway;

import com.may.gateway.utils.AutowiredBeanUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class GatewayApplication {
    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(GatewayApplication.class, args);
        AutowiredBeanUtil.setApplicationContext(applicationContext);
    }
}
