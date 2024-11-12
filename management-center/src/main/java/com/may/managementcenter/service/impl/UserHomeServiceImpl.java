package com.may.managementcenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.may.managementcenter.entity.UserHome;
import com.may.managementcenter.mapper.UserHomeMapper;
import com.may.managementcenter.service.UserHomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserHomeServiceImpl extends ServiceImpl<UserHomeMapper, UserHome> implements UserHomeService {

    @Autowired
    private UserHomeMapper userHomeMapper;

    @Override
    public UserHome selectByUserId(Integer userInfoId) {
        LambdaQueryWrapper<UserHome> wrapper = new LambdaQueryWrapper<UserHome>().eq(UserHome::getUserInfoId, userInfoId);
        return userHomeMapper.selectOne(wrapper);
    }
}
