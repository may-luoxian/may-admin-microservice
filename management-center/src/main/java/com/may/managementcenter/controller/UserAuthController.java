package com.may.managementcenter.controller;

import com.may.managementcenter.entity.UserInfo;
import com.may.managementcenter.service.UserInfoService;
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
