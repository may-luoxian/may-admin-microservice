package com.may.redisservice.controller;

import com.may.redisservice.service.RedisService;
import com.may.utils.model.dto.RedisDelDTO;
import com.may.utils.model.dto.RedisSetDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RedisController {
    @Autowired
    private RedisService redisService;

    @GetMapping("/expire")
    Boolean expire(@RequestParam("key") String key, @RequestParam("time") long time) {
        return redisService.expire(key, time);
    }

    @PostMapping("/hSet")
    Boolean hSet(@RequestBody RedisSetDTO redisSetDTO) {
        String key = redisSetDTO.getKey();
        String hashKey = redisSetDTO.getHashKey();
        Object value = redisSetDTO.getValue();
        long time = redisSetDTO.getTime();
        return redisService.hSet(key, hashKey, value, time);
    }

    @GetMapping("/hGet")
    Object hGet(@RequestParam("key") String key, @RequestParam("hashKey") String hashKey) {
        return redisService.hGet(key, hashKey);
    }

    @PostMapping("/hDel")
    void hDel(@RequestBody RedisDelDTO redisDelDTO) {
        String key = redisDelDTO.getKey();
        List<Object> hashKeyList = redisDelDTO.getHashKey();
        Object[] hashKeys = hashKeyList.toArray();
        redisService.hDel(key, hashKeys);
    }
}
