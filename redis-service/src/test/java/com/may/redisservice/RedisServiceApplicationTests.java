package com.may.redisservice;

import com.may.redisservice.service.RedisService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RedisServiceApplicationTests {

    @Autowired
    private RedisService redisService;

    @Test
    void contextLoads() {
    }

}
