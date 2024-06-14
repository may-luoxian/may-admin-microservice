package com.may.utils.feignapi;

import com.may.utils.model.dto.RedisDelDTO;
import com.may.utils.model.dto.RedisSetDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient("redis-service")
public interface RedisClient {
    @GetMapping("/expire")
    Boolean expire(@RequestParam("key") String key, @RequestParam("time") long time);

    @PostMapping("/hSet")
    Boolean hSet(@RequestBody RedisSetDTO redisSetDTO);

    @GetMapping("/hGet")
    Object hGet(@RequestParam("key") String key, @RequestParam("hashKey") String hashKey);

    @PostMapping("/hDel")
    void hDel(@RequestBody RedisDelDTO redisDelDTO);
}
