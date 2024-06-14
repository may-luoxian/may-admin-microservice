package com.may.managementcenter;

import com.may.utils.feignapi.RedisClient;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 系统管理中心
 */
@EnableFeignClients(clients = {RedisClient.class})
@SpringBootApplication
@MapperScan("com.may.managementcenter.mapper")
public class ManagementCenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManagementCenterApplication.class, args);
    }
}
