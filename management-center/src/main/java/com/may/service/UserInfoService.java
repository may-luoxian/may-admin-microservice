package com.may.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.may.entity.UserInfo;

import java.util.List;

public interface UserInfoService extends IService<UserInfo> {
    List<UserInfo> selectUserInfoList();
}
