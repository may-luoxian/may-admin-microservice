package com.may.managementcenter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.may.managementcenter.entity.RoleResource;
import com.may.managementcenter.mapper.RoleResourceMapper;
import com.may.managementcenter.service.RoleResourceService;
import org.springframework.stereotype.Service;


@Service
public class RoleResourceServiceImpl extends ServiceImpl<RoleResourceMapper, RoleResource> implements RoleResourceService {
}
