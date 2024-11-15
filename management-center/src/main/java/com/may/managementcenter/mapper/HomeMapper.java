package com.may.managementcenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.may.managementcenter.entity.Home;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author lx_syk
* @description 针对表【t_home】的数据库操作Mapper
* @createDate 2024-01-30 15:52:53
* @Entity com.myblog.entity.Home
*/
@Repository
public interface HomeMapper extends BaseMapper<Home> {
    List<Home> listEnableHomeByUser(@Param("userId") Integer id);

    List<Home> listNotEnableHomeByUser(@Param("userId") Integer id);

    List<Home> listEnalbeHomeByRole(@Param("roleId") Integer id);

    List<Home> listNotEnableHomeByRole(@Param("roleId") Integer id);
}




