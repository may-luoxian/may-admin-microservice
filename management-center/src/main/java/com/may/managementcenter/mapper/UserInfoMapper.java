package com.may.managementcenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.may.managementcenter.entity.UserInfo;
import com.may.managementcenter.model.dto.UserAdminDTO;
import com.may.managementcenter.model.vo.UserConditionVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserInfoMapper extends BaseMapper<UserInfo> {
    List<UserAdminDTO> selectUserInfoList(@Param("userConditionVO") UserConditionVO userConditionVO, @Param("current") Long current, @Param("size") Long size);
    Long countUser(@Param("userConditionVO") UserConditionVO conditionVO);

    int insertUserInfo(UserInfo userInfo);
}
