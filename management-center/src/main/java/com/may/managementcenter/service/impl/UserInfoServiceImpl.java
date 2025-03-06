package com.may.managementcenter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.may.managementcenter.entity.UserInfo;
import com.may.managementcenter.mapper.UserAuthMapper;
import com.may.managementcenter.mapper.UserInfoMapper;
import com.may.managementcenter.model.dto.UserAdminDTO;
import com.may.managementcenter.model.vo.UserConditionVO;
import com.may.managementcenter.service.UserInfoService;
import com.may.utils.PageUtil;
import com.may.utils.model.dto.PageResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public PageResultDTO<UserAdminDTO> selectUserInfoList(UserConditionVO userConditionVO) {
        Long limitCurrent = PageUtil.getLimitCurrent();
        Long size = PageUtil.getSize();
        Long count = userInfoMapper.countUser(userConditionVO);
        List<UserAdminDTO> userAdminDTOS = userInfoMapper.selectUserInfoList(userConditionVO, limitCurrent, size);
        return new PageResultDTO<>(userAdminDTOS, count);
    }
}
