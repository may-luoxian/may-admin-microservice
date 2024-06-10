package com.may.controller;

import com.may.entity.UserInfo;
import com.may.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserAuthController {

    @Autowired
    private UserInfoService userInfoService;
    @GetMapping("/list")
    public List<UserInfo> listUsers() {
        return userInfoService.selectUserInfoList();
    }
}
