package com.may.managementcenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.may.managementcenter.entity.*;
import com.may.managementcenter.mapper.*;
import com.may.managementcenter.model.dto.LabelOptionDTO;
import com.may.utils.model.dto.PageResultDTO;
import com.may.managementcenter.model.dto.RoleDTO;
import com.may.managementcenter.model.vo.MenuVO;
import com.may.managementcenter.model.vo.RoleVO;
import com.may.managementcenter.model.vo.UserVO;
import com.may.managementcenter.service.RoleMenuService;
import com.may.managementcenter.service.RoleResourceService;
import com.may.managementcenter.service.RoleService;
import com.may.managementcenter.service.UserRoleService;
import com.may.utils.Bean.BeanCopyUtil;
import com.may.utils.PageUtil;
import com.may.utils.TreeUtil;
import com.may.utils.exception.BizException;
import com.may.utils.feignapi.AuthResourceClient;
import com.may.utils.model.vo.ConditionVO;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static java.util.concurrent.CompletableFuture.supplyAsync;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private RoleMenuService roleMenuService;

    @Autowired
    private RoleResourceService roleResourceService;

    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private RoleResourceMapper roleResourceMapper;
    @Autowired
    private RoleMenuMapper roleMenuMapper;
    @Autowired
    private AuthResourceClient authResourceClient;

    @SneakyThrows
    @Override
    public PageResultDTO<RoleDTO> listRoles(ConditionVO conditionVO) {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<Role>()
                .like(StringUtils.isNotBlank(conditionVO.getKeywords()), Role::getRoleName, conditionVO.getKeywords());
        CompletableFuture<Long> supplyCount = supplyAsync(() -> roleMapper.selectCount(queryWrapper));
        List<RoleDTO> roleDTOs = roleMapper.listRoles(PageUtil.getLimitCurrent(), PageUtil.getSize(), conditionVO);
        return new PageResultDTO<>(roleDTOs, supplyCount.get());
    }

    @Override
    public List<LabelOptionDTO> listRoleMenus(MenuVO menuVO) {
        LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<Menu>().like(StringUtils.isNotBlank(menuVO.getName()), Menu::getName, menuVO.getName());
        List<Menu> menus = menuMapper.selectList(wrapper);
        if (StringUtils.isBlank(menuVO.getName())) {
            List<Menu> catalogs = listCatalogs(menus);
            Map<Integer, List<Menu>> childrenMap = getMenuMap(menus);
            HashMap<Integer, List<LabelOptionDTO>> labelOptionDTOListHashMap = new HashMap<>();
            childrenMap.forEach((id, menuList) -> {
                labelOptionDTOListHashMap.put(id, menuList.stream()
                        .map((item) -> LabelOptionDTO.builder()
                                .id(item.getId())
                                .label(item.getName())
                                .icon(item.getIcon())
                                .orderNum(item.getOrderNum())
                                .build())
                        .collect(Collectors.toList()));
            });
            List<LabelOptionDTO> rootList = catalogs.stream()
                    .map(item -> LabelOptionDTO.builder()
                            .id(item.getId())
                            .label(item.getName())
                            .icon(item.getIcon())
                            .orderNum(item.getOrderNum())
                            .build())
                    .sorted(Comparator.comparing(LabelOptionDTO::getOrderNum))
                    .collect(Collectors.toList());
            LabelOptionDTO t = new LabelOptionDTO();
            TreeUtil.buildTree(rootList, labelOptionDTOListHashMap, t, "children", false);
            return rootList;
        } else {
            return menus.stream().map(item -> LabelOptionDTO.builder()
                    .id(item.getId())
                    .label(item.getName())
                    .icon(item.getIcon())
                    .orderNum(item.getOrderNum())
                    .build()).collect(Collectors.toList());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveOrUpdateMenuAuth(Integer roleId, List<Integer> ids) {
        if (Objects.isNull(roleId)) {
            throw new BizException("未选择角色");
        }
        roleMenuService.remove(new LambdaQueryWrapper<RoleMenu>()
                .eq(RoleMenu::getRoleId, roleId));
        if (ids != null && ids.size() != 0) {
            List<RoleMenu> roleMenuList = ids.stream()
                    .map(id -> RoleMenu.builder()
                            .roleId(roleId)
                            .menuId(id)
                            .build())
                    .collect(Collectors.toList());
            roleMenuService.saveBatch(roleMenuList);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveOrUpdateResourceAuth(Integer roleId, List<Integer> ids) {
        if (Objects.isNull(roleId)) {
            throw new BizException("未选择角色");
        }
        roleResourceService.remove(new LambdaQueryWrapper<RoleResource>()
                .eq(RoleResource::getRoleId, roleId));
        if (ids != null && ids.size() != 0) {
            List<RoleResource> roleResourceList = ids.stream()
                    .map(id -> RoleResource.builder()
                            .roleId(roleId)
                            .resourceId(id)
                            .build())
                    .collect(Collectors.toList());
            roleResourceService.saveBatch(roleResourceList);
        }
        authResourceClient.clearResource();
    }

    @Override
    public List<RoleDTO> listAllowRoles() {
        LambdaQueryWrapper<Role> lambdaQueryWrapper = new LambdaQueryWrapper<Role>()
                .eq(Role::getIsDisable, 0);
        List<Role> roles = roleMapper.selectList(lambdaQueryWrapper);
        return BeanCopyUtil.copyList(roles, RoleDTO.class);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateAllowRoles(UserVO userVO) {
        userRoleService.remove(new LambdaQueryWrapper<UserRole>()
                .eq(UserRole::getUserId, userVO.getId()));
        List<Integer> roleIds = userVO.getRoleIds();
        if (roleIds != null && roleIds.size() != 0) {
            List<UserRole> userRoles = roleIds.stream()
                    .map((id) -> UserRole
                            .builder()
                            .userId(userVO.getId())
                            .roleId(id)
                            .build())
                    .collect(Collectors.toList());
            userRoleService.saveBatch(userRoles);
        }
    }

    @Override
    public void saveOrUpdateRole(RoleVO roleVO) {
        Role roleCheck = roleMapper.selectOne(new LambdaQueryWrapper<Role>()
                .select(Role::getId, Role::getRoleName)
                .eq(Role::getRoleName, roleVO.getRoleName()));
        if (Objects.nonNull(roleCheck) && !(roleCheck.getId().equals(roleVO.getId()))) {
            throw new BizException("该角色已存在");
        }
        Role role = Role.builder()
                .id(roleVO.getId())
                .roleName(roleVO.getRoleName())
                .isDisable(roleVO.getIsDisable())
                .describe(roleVO.getDescribe())
                .build();
        this.saveOrUpdate(role);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteRoles(List<Integer> ids) {
        Long count = userRoleMapper.selectCount(new LambdaQueryWrapper<UserRole>()
                .in(UserRole::getRoleId, ids));
        if (count > 0) {
            throw new BizException("所选角色中有角色已经绑定了用户");
        }
        roleResourceMapper.batchDeleteByRoleids(ids);
        roleMenuMapper.batchDeleteByRoleids(ids);
        roleMapper.deleteBatchIds(ids);
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
