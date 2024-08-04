package com.may.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.may.auth.entity.Role;
import com.may.auth.model.dto.ResourceRoleDTO;
import com.may.auth.model.dto.RoleDTO;
import com.may.utils.model.vo.ConditionVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleMapper extends BaseMapper<Role> {
    List<ResourceRoleDTO> listResourceRoles();

    List<String> listRolesByUserInfoId(@Param("userInfoId") Integer userInfoId);

    List<RoleDTO> listRoles(@Param("current") Long pageLimit, @Param("size") Long size, @Param("conditionVO") ConditionVO conditionVO);
}
