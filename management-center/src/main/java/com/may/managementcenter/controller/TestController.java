package com.may.managementcenter.controller;

import com.may.utils.feignapi.RedisClient;
import com.may.utils.model.dto.RedisDelDTO;
import com.may.utils.model.dto.RedisSetDTO;
import com.may.utils.model.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private RedisClient redisClient;

    @GetMapping("/expire")
    public ResultVO<?> expire(@RequestParam("key") String key, @RequestParam("time") long time) {
        Boolean expire = redisClient.expire(key, time);
        return ResultVO.ok(expire);
    }

    @PostMapping("/hSet")
    public ResultVO<?> hSet(@RequestBody RedisSetDTO redisSetDTO) {
        Boolean isSuccess = redisClient.hSet(redisSetDTO);
        return ResultVO.ok(isSuccess);
    }

    @GetMapping("/hGet")
    public ResultVO<?> hGet(@RequestParam("key") String key, @RequestParam("hashKey") String hashKey) {
        Object result = redisClient.hGet(key, hashKey);
        return ResultVO.ok(result);
    }

    @PostMapping("/hDel")
    public ResultVO<?> hDel(@RequestBody RedisDelDTO redisDelDTO) {
        redisClient.hDel(redisDelDTO);
        return ResultVO.ok();
    }
}
