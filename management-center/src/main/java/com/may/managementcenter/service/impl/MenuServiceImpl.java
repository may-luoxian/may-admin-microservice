package com.may.managementcenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.may.managementcenter.entity.Menu;
import com.may.managementcenter.mapper.MenuMapper;
import com.may.managementcenter.mapper.RoleMenuMapper;
import com.may.managementcenter.model.dto.MenuDTO;
import com.may.managementcenter.model.dto.UserMenuDTO;
import com.may.managementcenter.model.vo.MenuVO;
import com.may.managementcenter.service.MenuService;
import com.may.utils.Bean.BeanCopyUtil;
import com.may.utils.TreeUtil;
import com.may.utils.feignapi.RedisClient;
import com.may.utils.getuserdetails.UserInfoContext;
import com.may.utils.model.dto.UserDetailsShareDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private RoleMenuMapper roleMenuMapper;
    //获取用户菜单
    @Override
    public List<UserMenuDTO> listUserMenus() {
        List<Menu> menus = menuMapper.listMenusByUserInfoId(UserInfoContext.getUser().getUserInfoId());
        List<Menu> catalogs = listCatalogs(menus);
        Map<Integer, List<Menu>> childrenMap = getMenuMap(menus);
        List<UserMenuDTO> rootList = catalogs.stream()
                .map(item -> BeanCopyUtil.copyObject(item, UserMenuDTO.class))
                .sorted(Comparator.comparing(UserMenuDTO::getOrderNum))
                .collect(Collectors.toList());
        Map<Integer, List<UserMenuDTO>> childDTOMap = new HashMap<>();
        childrenMap.forEach((id, menuList) -> {
            List<UserMenuDTO> menuDTOList = BeanCopyUtil.copyList(menuList, UserMenuDTO.class);
            childDTOMap.put(id, menuDTOList);
        });
        UserMenuDTO t = new UserMenuDTO();
        TreeUtil.buildTree(rootList, childDTOMap, t, "children", false);
        return rootList;
    }

    @Override
    public List<MenuDTO> listMenus(MenuVO menuVO) {
        LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<Menu>()
                .like(StringUtils.isNotBlank(menuVO.getName()), Menu::getName, menuVO.getName())
                .like(menuVO.getMenuType() != null, Menu::getMenuType, menuVO.getMenuType());
        List<Menu> menus = menuMapper.selectList(wrapper);
        if (StringUtils.isNotBlank(menuVO.getName()) || menuVO.getMenuType() != null) {
            return BeanCopyUtil.copyList(menus, MenuDTO.class);
        } else {
            List<Menu> catalogs = listCatalogs(menus);
            Map<Integer, List<Menu>> childrenMap = getMenuMap(menus);
            List<MenuDTO> rootList = catalogs.stream()
                    .map(item -> BeanCopyUtil.copyObject(item, MenuDTO.class))
                    .sorted(Comparator.comparing(MenuDTO::getOrderNum))
                    .collect(Collectors.toList());
            Map<Integer, List<MenuDTO>> childDTOMap = new HashMap<>();
            childrenMap.forEach((id, menuList) -> {
                List<MenuDTO> menuDTOList = BeanCopyUtil.copyList(menuList, MenuDTO.class);
                childDTOMap.put(id, menuDTOList);
            });
            MenuDTO t = new MenuDTO();
            TreeUtil.buildTree(rootList, childDTOMap, t, "children", false);
            return rootList;
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveOrUpdateMenu(MenuVO menuVO) {
        Menu menu = BeanCopyUtil.copyObject(menuVO, Menu.class);
        this.saveOrUpdate(menu);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteMenu(List<Integer> ids) {
        roleMenuMapper.batchDeleteByMenuIds(ids);
        menuMapper.deleteBatchIds(ids);
    }

    private List<Menu> listCatalogs(List<Menu> menus) {
        return menus.stream()
                .filter(item -> Objects.isNull(item.getParentId()))
                .sorted(Comparator.comparing(Menu::getOrderNum))
                .collect(Collectors.toList());
    }

    private Map<Integer, List<Menu>> getMenuMap(List<Menu> menus) {
        return menus.stream()
                .filter(item -> Objects.nonNull(item.getParentId()))
                .collect(Collectors.groupingBy(Menu::getParentId));
    }
}
