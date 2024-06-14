package com.may.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.may.auth.entity.Role;
import com.may.auth.model.dto.ResourceRoleDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleMapper extends BaseMapper<Role> {
    List<ResourceRoleDTO> listResourceRoles();
}
