package com.may.managementcenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.may.managementcenter.entity.Menu;
import com.may.managementcenter.model.dto.MenuDTO;
import com.may.managementcenter.model.dto.UserMenuDTO;
import com.may.managementcenter.model.vo.MenuVO;

import java.util.List;

public interface MenuService extends IService<Menu> {
    //获取用户菜单列表
    List<UserMenuDTO> listUserMenus();

    // 获取系统菜单列表
    List<MenuDTO> listMenus(MenuVO menuVO);

    // 新增或修改菜单
    void saveOrUpdateMenu(MenuVO menuVO);

    // 删除菜单
    void deleteMenu(List<Integer> ids);
}
