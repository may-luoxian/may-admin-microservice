package com.may.managementcenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.may.managementcenter.entity.UserHome;

public interface UserHomeService extends IService<UserHome> {
    UserHome selectByUserId(Integer userInfoId);
}
