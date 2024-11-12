package com.may.managementcenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.may.managementcenter.entity.RoleMenu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {
    long batchDeleteByRoleids(@Param("ids") List<Integer> ids);

    long batchDeleteByMenuIds(@Param("ids") List<Integer> ids);
}
