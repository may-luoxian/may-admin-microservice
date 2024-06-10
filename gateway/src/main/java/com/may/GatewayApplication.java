package com.may;

import com.may.utils.AutowiredBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class GatewayApplication {
    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(GatewayApplication.class, args);
        AutowiredBean.setApplicationContext(applicationContext);
    }
}
