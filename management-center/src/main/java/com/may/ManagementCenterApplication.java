package com.may;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 系统管理中心
 */
@SpringBootApplication
@MapperScan("com.may.mapper")
public class ManagementCenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManagementCenterApplication.class, args);
    }
}
