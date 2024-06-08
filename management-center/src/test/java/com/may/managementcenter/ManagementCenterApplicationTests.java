package com.may.managementcenter;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ManagementCenterApplicationTests {
    @Value("${spring.datasource.url}")
    private String url;

    @Test
    void contextLoads() {
        System.out.println(url);
    }

}
