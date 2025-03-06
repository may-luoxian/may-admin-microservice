package com.may.managementcenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.may.managementcenter.entity.UserInfo;
import com.may.managementcenter.model.dto.UserAdminDTO;
import com.may.managementcenter.model.vo.UserConditionVO;
import com.may.utils.model.dto.PageResultDTO;

import java.util.List;

public interface UserInfoService extends IService<UserInfo> {
    PageResultDTO<UserAdminDTO> selectUserInfoList(UserConditionVO userConditionVO);
}
