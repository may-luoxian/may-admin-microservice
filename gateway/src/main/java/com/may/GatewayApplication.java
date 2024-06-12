package com.may;

import com.may.utils.AutowiredBeanUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class GatewayApplication {
    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(GatewayApplication.class, args);
        AutowiredBeanUtil.setApplicationContext(applicationContext);
    }
}
