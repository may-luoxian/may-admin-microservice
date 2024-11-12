package com.may.managementcenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.may.managementcenter.entity.Home;
import com.may.managementcenter.model.dto.HomeDTO;
import com.may.managementcenter.model.dto.HomeEnableAndNotEnableDTO;
import com.may.managementcenter.model.vo.HomeOrderVO;
import com.may.managementcenter.model.vo.HomeVO;

import java.util.List;

/**
* @author lx_syk
* @description 针对表【t_home】的数据库操作Service
* @createDate 2024-01-30 15:52:53
*/
public interface HomeService extends IService<Home> {
    void createModel(HomeVO homeVO);

    void editModel(HomeVO homeVO);

    HomeEnableAndNotEnableDTO getEnableNotEnableListByUser(Integer userId);

    HomeEnableAndNotEnableDTO getEnableNotEnableListByRole(Integer roleId);

    void enableUserHome(Integer userId, List<HomeOrderVO> homeOrderList);

    void enableRoleHome(Integer roleId, List<HomeOrderVO> homeOrderList);

    void deleteHomeById(Integer id);

    List<HomeDTO> getHomeList();
}
