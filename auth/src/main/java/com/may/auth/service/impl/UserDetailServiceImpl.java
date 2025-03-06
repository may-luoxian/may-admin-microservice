package com.may.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.may.auth.entity.UserAuth;
import com.may.auth.entity.UserInfo;
import com.may.auth.mapper.RoleMapper;
import com.may.auth.mapper.UserAuthMapper;
import com.may.auth.mapper.UserInfoMapper;
import com.may.auth.model.dto.UserDetailsDTO;
import com.may.auth.utils.IpUtil;
import com.may.utils.exception.BizException;
import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private UserAuthMapper userAuthMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Resource
    private HttpServletRequest request;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (StringUtils.isBlank(username)) {
            throw new BizException("用户名不能为空");
        }
        UserAuth userAuth = userAuthMapper.selectOne(new LambdaQueryWrapper<UserAuth>()
                .select(UserAuth::getId, UserAuth::getUserInfoId, UserAuth::getUsername, UserAuth::getPassword, UserAuth::getLoginType)
                .eq(UserAuth::getUsername, username));
        if (Objects.isNull(userAuth)) {
            throw new BizException("用户不存在");
        }

        return convertUserDetail(userAuth, request);
    }

    public UserDetailsDTO convertUserDetail(UserAuth user, HttpServletRequest request) {
        UserInfo userInfo = userInfoMapper.selectById(user.getUserInfoId());
        List<String> roles = roleMapper.listRolesByUserInfoId(userInfo.getId());
        String ipAddress = IpUtil.getIpAddress(request);
        String ipSource = IpUtil.getIpSource(ipAddress);
        UserAgent userAgent = IpUtil.getUserAgent(request);

        UserDetailsDTO userDetailsDTO = new UserDetailsDTO();
        userDetailsDTO.setId(user.getId());
        userDetailsDTO.setLoginType(user.getLoginType());
        userDetailsDTO.setUserInfoId(userInfo.getId());
        userDetailsDTO.setUsername(user.getUsername());
        userDetailsDTO.setPassword(user.getPassword());
        userDetailsDTO.setEmail(userInfo.getEmail());
        userDetailsDTO.setRoles(roles);
        userDetailsDTO.setNickname(userInfo.getNickname());
        userDetailsDTO.setAvatar(userInfo.getAvatar());
        userDetailsDTO.setIntro(userInfo.getIntro());
        userDetailsDTO.setWebsite(userInfo.getWebsite());
        userDetailsDTO.setIsSubscribe(userInfo.getIsSubscribe());
        userDetailsDTO.setIpAddress(ipAddress);
        userDetailsDTO.setIpSource(ipSource);
        userDetailsDTO.setIsDisable(userInfo.getIsDisable());
        userDetailsDTO.setBrowser(userAgent.getBrowser().getName());
        userDetailsDTO.setOs(userAgent.getOperatingSystem().getName());
        userDetailsDTO.setLastLoginTime(LocalDateTime.now());

        return userDetailsDTO;
    }
}
