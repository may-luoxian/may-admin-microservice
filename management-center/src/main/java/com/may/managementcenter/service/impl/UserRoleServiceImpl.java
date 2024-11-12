package com.may.managementcenter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.may.managementcenter.entity.UserRole;
import com.may.managementcenter.mapper.UserRoleMapper;
import com.may.managementcenter.service.UserRoleService;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {
}
