package com.may.managementcenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.may.managementcenter.entity.UserAuth;
import com.may.managementcenter.model.dto.UserAreaDTO;
import com.may.managementcenter.model.dto.UserDetailsDTO;
import com.may.managementcenter.model.dto.UserInfoDTO;
import com.may.utils.model.vo.ConditionVO;

import java.util.List;

public interface UserAuthService extends IService<UserAuth> {

    void logout(Integer id);

    void createUser(UserDetailsDTO userDetailsDTO);

    List<UserAreaDTO> listUserAreas(ConditionVO conditionVO);

    void deleteUser(Integer id);

    UserInfoDTO userInfo();
}
