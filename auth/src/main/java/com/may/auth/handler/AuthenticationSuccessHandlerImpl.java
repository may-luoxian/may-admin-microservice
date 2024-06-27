package com.may.auth.handler;

import com.alibaba.fastjson.JSON;
import com.may.auth.entity.UserAuth;
import com.may.auth.mapper.UserAuthMapper;
import com.may.auth.model.dto.UserDetailsDTO;
import com.may.auth.model.dto.UserInfoDTO;
import com.may.auth.service.TokenService;
import com.may.auth.utils.UserUtil;
import com.may.utils.Bean.BeanCopyUtil;
import com.may.utils.constant.CommonConstant;
import com.may.utils.model.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {
    @Autowired
    private UserAuthMapper userAuthMapper;

    @Autowired
    private TokenService tokenService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        // 构建用户登录响应数据
        UserInfoDTO userLoginDTO = BeanCopyUtil.copyObject(UserUtil.getUserDetailsDTO(), UserInfoDTO.class);
        if(Objects.nonNull(userLoginDTO)) {
            // 保存用户信息生成token
            UserDetailsDTO userDetailsDTO = (UserDetailsDTO) authentication.getPrincipal();
            String token = tokenService.createToken(userDetailsDTO);
            userLoginDTO.setToken(token);
        }
        response.setContentType(CommonConstant.APPLICATION_JSON);
        response.getWriter().write(JSON.toJSONString(ResultVO.ok(userLoginDTO)));
        updateUserInfo();
    }

    /**
     * 更新数据库用户权限数据
     */
    @Async
    public void updateUserInfo() {
        UserAuth userAuth = new UserAuth();
        UserDetailsDTO userDetailsDTO = UserUtil.getUserDetailsDTO();
        userAuth.setId(userDetailsDTO.getId());
        userAuth.setIpAddress(userDetailsDTO.getIpAddress());
        userAuth.setIpSource(userDetailsDTO.getIpSource());
        userAuth.setLastLoginTime(userDetailsDTO.getLastLoginTime());
        userAuthMapper.updateById(userAuth);
    }
}
