package com.may.managementcenter.filter;

import com.alibaba.fastjson.JSON;
import com.may.utils.feignapi.RedisClient;
import com.may.utils.getuserdetails.UserInfoContext;
import com.may.utils.model.dto.UserDetailsShareDTO;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.may.utils.TokenUtils.parseToken;
import static com.may.utils.constant.AuthConstant.TOKEN_HEADER;
import static com.may.utils.constant.RedisConstant.LOGIN_USER;

public class LoginHandlerInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisClient redisClient;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader(TOKEN_HEADER);
        if (token == null || token.isEmpty()) {
            return false;
        }

        Claims claims = parseToken(token);
        String userId = claims.getSubject();

        Object o = redisClient.hGet(LOGIN_USER, userId);
        UserDetailsShareDTO userDetailsShareDTO = JSON.parseObject(JSON.toJSONString(o), UserDetailsShareDTO.class);
        UserInfoContext.setUser(userDetailsShareDTO);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserInfoContext.remove();
    }
}
