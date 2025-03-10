package com.may.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.may.auth.entity.UserInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserInfoMapper extends BaseMapper<UserInfo> {
}
