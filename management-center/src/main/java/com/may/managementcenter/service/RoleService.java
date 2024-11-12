package com.may.managementcenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.may.managementcenter.entity.Role;
import com.may.managementcenter.model.dto.LabelOptionDTO;
import com.may.managementcenter.model.dto.PageResultDTO;
import com.may.managementcenter.model.dto.RoleDTO;
import com.may.managementcenter.model.vo.MenuVO;
import com.may.managementcenter.model.vo.RoleVO;
import com.may.managementcenter.model.vo.UserVO;
import com.may.utils.model.vo.ConditionVO;

import java.util.List;

public interface RoleService extends IService<Role> {
    PageResultDTO<RoleDTO> listRoles(ConditionVO conditionVO);
    List<LabelOptionDTO> listRoleMenus(MenuVO menuVO);
    void saveOrUpdateMenuAuth(Integer roleId, List<Integer> ids);
    void saveOrUpdateResourceAuth(Integer roleId, List<Integer> ids);
    List<RoleDTO> listAllowRoles();
    void updateAllowRoles(UserVO userVO);
    void saveOrUpdateRole(RoleVO roleVO);
    void deleteRoles(List<Integer> ids);
}
