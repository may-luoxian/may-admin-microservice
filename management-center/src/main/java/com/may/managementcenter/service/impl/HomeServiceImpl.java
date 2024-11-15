package com.may.managementcenter.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.may.managementcenter.entity.Home;
import com.may.managementcenter.entity.RoleHome;
import com.may.managementcenter.entity.UserHome;
import com.may.managementcenter.mapper.HomeMapper;
import com.may.managementcenter.model.dto.HomeDTO;
import com.may.managementcenter.model.dto.HomeEnableAndNotEnableDTO;
import com.may.managementcenter.model.vo.HomeOrderVO;
import com.may.managementcenter.model.vo.HomeVO;
import com.may.managementcenter.service.HomeService;
import com.may.managementcenter.service.RoleHomeService;
import com.may.managementcenter.service.UserHomeService;
import com.may.utils.Bean.BeanCopyUtil;
import com.may.utils.enums.StatusCodeEnum;
import com.may.utils.exception.BizException;
import com.may.utils.getuserdetails.UserInfoContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author lx_syk
 * @description 针对表【t_home】的数据库操作Service实现
 * @createDate 2024-01-30 15:52:53
 */
@Service
public class HomeServiceImpl extends ServiceImpl<HomeMapper, Home> implements HomeService {

    @Autowired
    private HomeMapper homeMapper;

    @Autowired
    private UserHomeService userHomeService;

    @Autowired
    private RoleHomeService roleHomeService;

    @Override
    public void createModel(HomeVO homeVO) {
        if (ObjectUtils.isEmpty(homeVO)) {
            throw new BizException(StatusCodeEnum.VALID_ERROR.getDesc());
        }
        Home home = BeanCopyUtil.copyObject(homeVO, Home.class);
        this.save(home);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void editModel(HomeVO homeVO) {
        if (ObjectUtils.isEmpty(homeVO)) {
            throw new BizException(StatusCodeEnum.VALID_ERROR.getDesc());
        }
        if (homeVO.getControlStatus() == 1) {
            UserHome userHome = userHomeService.selectByUserId(homeVO.getUserId());
            List<HomeOrderVO> homeOrderVOList = new ArrayList<>();
            HomeOrderVO homeOrderVO = new HomeOrderVO();
            homeOrderVO.setHomeId(homeVO.getId());
            homeOrderVO.setWidthValue(homeVO.getWidthValue());
            if (userHome != null) {
                boolean flag = false;
                String homeOrder = userHome.getHomeOrder();
                homeOrderVOList = JSONUtil.toList(homeOrder, HomeOrderVO.class);
                for (HomeOrderVO item : homeOrderVOList) {
                    if (item.getHomeId() == homeVO.getId()) {
                        item.setWidthValue(homeVO.getWidthValue());
                        flag = true;
                    }
                }
                if (!flag) {
                    homeOrderVOList.add(homeOrderVO);
                }
                String str = JSONUtil.toJsonStr(homeOrderVOList);
                userHome.setHomeOrder(str);
                LambdaUpdateWrapper<UserHome> updateWrapper = new LambdaUpdateWrapper<UserHome>()
                        .eq(UserHome::getUserInfoId, homeVO.getUserId());
                userHomeService.update(userHome, updateWrapper);
            } else {
                UserHome userHome1 = new UserHome();
                userHome1.setUserInfoId(homeVO.getUserId());
                homeOrderVOList.add(homeOrderVO);
                String str = JSONUtil.toJsonStr(homeOrderVOList);
                userHome1.setHomeOrder(str);
                userHomeService.save(userHome1);
            }
        } else if (homeVO.getControlStatus() == 3) {
            LambdaUpdateWrapper<RoleHome> wrapper = new LambdaUpdateWrapper<RoleHome>()
                    .set(RoleHome::getWidthValue, homeVO.getWidthValue())
                    .eq(RoleHome::getRoleId, homeVO.getRoleId())
                    .eq(RoleHome::getHomeId, homeVO.getId());
            roleHomeService.update(wrapper);
        }
        Home home = BeanCopyUtil.copyObject(homeVO, Home.class);
        this.updateById(home);
    }

    @Override
    public HomeEnableAndNotEnableDTO getEnableNotEnableListByUser(Integer userId) {
        List<Home> notEnableList = homeMapper.listNotEnableHomeByUser(userId);
        List<HomeDTO> notEnableDTO = BeanCopyUtil.copyList(notEnableList, HomeDTO.class);
        List<HomeDTO> enableDTO = getOrderHomeList(userId);
        UserHome userHome = userHomeService.selectByUserId(userId);
        if (userHome != null && userHome.getHomeOrder() != null) {
            List<HomeOrderVO> homeOrderVOList = JSONUtil.toList(userHome.getHomeOrder(), HomeOrderVO.class);
            enableDTO.stream()
                    .forEach(homeDTO -> {
                        homeOrderVOList.stream().forEach(homeOrderVO -> {
                            if (homeDTO.getId() == homeOrderVO.getHomeId()) {
                                homeDTO.setWidthValue(homeOrderVO.getWidthValue());
                            }
                        });
                    });
        }
        return HomeEnableAndNotEnableDTO.builder()
                .enableList(enableDTO)
                .notEnableList(notEnableDTO)
                .build();
    }

    @Override
    public HomeEnableAndNotEnableDTO getEnableNotEnableListByRole(Integer roleId) {
        List<Home> enableList = homeMapper.listEnalbeHomeByRole(roleId);
        List<HomeDTO> enableDTO = BeanCopyUtil.copyList(enableList, HomeDTO.class);
        List<Home> notEnableList = homeMapper.listNotEnableHomeByRole(roleId);
        List<HomeDTO> notEnableDTO = BeanCopyUtil.copyList(notEnableList, HomeDTO.class);
        return HomeEnableAndNotEnableDTO.builder()
                .enableList(enableDTO)
                .notEnableList(notEnableDTO)
                .build();
    }

    @Override
    public void enableUserHome(Integer userId, List<HomeOrderVO> homeOrderList) {
        UserHome userHome = UserHome.builder()
                .userInfoId(userId)
                .homeOrder(JSONUtil.toJsonStr(homeOrderList))
                .build();
        LambdaUpdateWrapper<UserHome> updateWrapper = new LambdaUpdateWrapper<UserHome>().eq(UserHome::getUserInfoId, userId);
        userHomeService.saveOrUpdate(userHome, updateWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void enableRoleHome(Integer roleId, List<HomeOrderVO> homeOrderList) {
        if (roleId == null) {
            throw new BizException("未选择角色");
        }
        LambdaQueryWrapper<RoleHome> removeLambda = new LambdaQueryWrapper<RoleHome>().eq(RoleHome::getRoleId, roleId);
        roleHomeService.remove(removeLambda);
        if (homeOrderList != null && homeOrderList.size() != 0) {
            List<RoleHome> collect = homeOrderList.stream().map(item -> RoleHome.builder()
                            .roleId(roleId)
                            .homeId(item.getHomeId())
                            .widthValue(item.getWidthValue())
                            .orderNum(item.getOrderNum())
                            .build())
                    .collect(Collectors.toList());
            roleHomeService.saveBatch(collect);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteHomeById(Integer id) {
        if (id == null) {
            throw new BizException("id不能为空");
        }
        this.removeById(id);
        roleHomeService.remove(new LambdaQueryWrapper<RoleHome>()
                .eq(RoleHome::getHomeId, id));
    }

    @Override
    public List<HomeDTO> getHomeList() {
        Integer userInfoId = UserInfoContext.getUser().getUserInfoId();
        return getOrderHomeList(userInfoId);
    }

    private List<HomeDTO> getOrderHomeList(Integer userId) {
        List<Home> enableList = homeMapper.listEnableHomeByUser(userId);
        List<HomeDTO> enableDTO = BeanCopyUtil.copyList(enableList, HomeDTO.class);
        // 启用模块需要根据userhome表排序
        UserHome userHome = userHomeService.selectByUserId(userId);
        if (ObjectUtils.isEmpty(userHome)) {
            return enableDTO;
        }
        String homeOrder = userHome.getHomeOrder();
        if (homeOrder != null) {
            List<HomeOrderVO> homeOrderVOList = JSONUtil.toList(homeOrder, HomeOrderVO.class);
            homeOrderVOList.stream().sorted(Comparator.comparing(HomeOrderVO::getOrderNum));
            List<HomeDTO> orderList = new ArrayList<>();
            homeOrderVOList.stream().forEach((item) -> {
                enableDTO.stream().forEach(enableHome -> {
                    if (ObjectUtils.isNotEmpty(enableHome) && item.getHomeId().equals(enableHome.getId())) {
                        enableHome.setWidthValue(item.getWidthValue());
                        orderList.add(enableHome);
                    }
                });
            });
            return Stream.concat(orderList.stream(), enableDTO.stream())
                    .distinct()
                    .collect(Collectors.toList());
        }
        return enableDTO;
    }
}




