package com.may.managementcenter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.may.managementcenter.entity.UserInfo;
import com.may.managementcenter.mapper.UserInfoMapper;
import com.may.managementcenter.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;
    @Override
    public List<UserInfo> selectUserInfoList() {
        return userInfoMapper.selectUserInfoList();
    }
}
