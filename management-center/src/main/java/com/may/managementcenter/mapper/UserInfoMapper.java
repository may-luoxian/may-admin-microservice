package com.may.managementcenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.may.managementcenter.entity.UserInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserInfoMapper extends BaseMapper<UserInfo> {
    List<UserInfo> selectUserInfoList();
}
