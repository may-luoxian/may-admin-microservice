package com.may.managementcenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.may.managementcenter.entity.Menu;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuMapper extends BaseMapper<Menu> {
    List<Menu> listMenusByUserInfoId(Integer userInfoId);
}
