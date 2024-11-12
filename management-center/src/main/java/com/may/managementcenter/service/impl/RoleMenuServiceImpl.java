package com.may.managementcenter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.may.managementcenter.entity.RoleMenu;
import com.may.managementcenter.mapper.RoleMenuMapper;
import com.may.managementcenter.service.RoleMenuService;
import org.springframework.stereotype.Service;

@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {
}
