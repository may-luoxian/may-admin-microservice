package com.may.utils.feignapi;

import com.may.utils.model.dto.RedisDelDTO;
import com.may.utils.model.dto.RedisSetDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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

    @GetMapping("/incr")
    Long incr(@RequestParam("key") String key, @RequestParam("delta") long delta);

    @PostMapping("/sIsMember")
    Boolean sIsMember(@RequestBody RedisSetDTO redisSetDTO);

    @GetMapping("/hIncr")
    Long hIncr(@RequestParam("key") String key, @RequestParam("hashKey") String hashKey, @RequestParam("delta") Long delta);

    @PostMapping("/sAdd")
    Long sAdd(@RequestBody RedisSetDTO redisSetDTO);

    @GetMapping("/get")
    Object get(@RequestParam("key") String key);

    @GetMapping("/hGetAll")
    Map hGetAll(@RequestParam("key") String key);
}
