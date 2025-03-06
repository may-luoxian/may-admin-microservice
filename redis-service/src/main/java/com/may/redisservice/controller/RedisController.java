package com.may.redisservice.controller;

import com.may.redisservice.service.RedisService;
import com.may.utils.model.dto.RedisDelDTO;
import com.may.utils.model.dto.RedisSetDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class RedisController {
    @Autowired
    private RedisService redisService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

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

    @GetMapping("/incr")
    Long incr(@RequestParam("key") String key, @RequestParam("delta") long delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }

    @PostMapping("/sIsMember")
    public Boolean sIsMember(@RequestBody RedisSetDTO redisSetDTO) {
        String key = redisSetDTO.getKey();
        Object value = redisSetDTO.getValue();
        return redisTemplate.opsForSet().isMember(key, value);
    }

    @GetMapping("/hIncr")
    public Long hIncr(@RequestParam("key") String key, @RequestParam("hashKey") String hashKey, @RequestParam("delta") Long delta) {
        return redisTemplate.opsForHash().increment(key, hashKey, delta);
    }

    @PostMapping("/sAdd")
    public Long sAdd(@RequestBody RedisSetDTO redisSetDTO) {
        String key = redisSetDTO.getKey();
        Object value = redisSetDTO.getValue();
        return redisTemplate.opsForSet().add(key, value);
    }

    @GetMapping("/get")
    Object get(@RequestParam("key") String key) {
        return redisService.get(key);
    }

    @GetMapping("/hGetAll")
    Map hGetAll(@RequestParam("key") String key) {
        return redisService.hGetAll(key);
    }
}
