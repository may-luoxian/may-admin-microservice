package com.may.auth.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.may.auth.model.dto.UserDetailsDTO;
import com.may.auth.service.TokenService;
import com.may.utils.feignapi.RedisClient;
import com.may.utils.model.dto.RedisSetDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static com.may.utils.constant.AuthConstant.*;
import static com.may.utils.constant.RedisConstant.LOGIN_USER;

@Service
public class TokenServiceImpl implements TokenService {
    @Autowired
    private RedisClient redisClient;

    /**
     * 生成token，更新redis中当前键为userid的token
     */
    @Override
    public String createToken(UserDetailsDTO userDetailsDTO) {
        String userId = userDetailsDTO.getId().toString();
        String token = createToken(userId);
        userDetailsDTO.setToken(token);
        refreshToken(userDetailsDTO);
        return token;
    }

    @Override
    public String createToken(String subject) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        SecretKey secretKey = generalKey();
        Date date = new Date();
        return Jwts.builder().setId(getUuid()).setSubject(subject)
                .setIssuer("sunyukun").setIssuedAt(date)
                .signWith(signatureAlgorithm, secretKey).compact();
    }

    // 根据生成token时使用的密钥逆向解密token
    @Override
    public Claims parseToken(String token) {
        SecretKey secretKey = generalKey();
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }

    @Override
    public void renewToken(UserDetailsDTO userDetailsDTO) {
        LocalDateTime expireTime = userDetailsDTO.getExpireTime(); // 过期时间
        LocalDateTime currentTime = LocalDateTime.now(); // 当前时间
        if (Duration.between(currentTime, expireTime).toMinutes() <= TWENTY_MINUTES) { // 如果当前时间距离结束时间小于20分钟，则更新token过期时间
            refreshToken(userDetailsDTO);
        }
    }

    /**
     * 更新token过期时间
     */
    @Override
    public void refreshToken(UserDetailsDTO userDetailsDTO) {
        LocalDateTime currentTime = LocalDateTime.now(); // 获取当前时间
        userDetailsDTO.setExpireTime(currentTime.plusSeconds(EXPIRE_TIME)); // 设置过期时间为当前时间加7小时
        String userId = userDetailsDTO.getId().toString();

        RedisSetDTO redisSetDTO = new RedisSetDTO();
        redisSetDTO.setKey(LOGIN_USER);
        redisSetDTO.setHashKey(userId);
        redisSetDTO.setValue(userDetailsDTO);
        redisSetDTO.setTime(EXPIRE_TIME);
        redisClient.hSet(redisSetDTO);
    }

    /**
     * 获取请求头中的token
     * 根据token中的userId获取redis中该id存储的用户信息，构建userDetailDTO
     */
    @Override
    public UserDetailsDTO getUserDetailDTO(HttpServletRequest request) {
        // 从请求中获取token，并将token前的bearer 删除，如果为空则返回“”，否则返回token
        String token = Optional.ofNullable(request.getHeader(TOKEN_HEADER)).orElse("").replaceFirst(TOKEN_PREFIX, "");
        // 如果token非空，从redis中通过userid获取该用户信息，构建UserDetailDTO
        if (StringUtils.hasText(token) && !token.equals("null")) {
            Claims claims = parseToken(token);
            String userId = claims.getSubject();
            Object o = redisClient.hGet(LOGIN_USER, userId);
            ObjectMapper objectMapper = new ObjectMapper();
            UserDetailsDTO userDetailsDTO = objectMapper.convertValue(o, UserDetailsDTO.class);
            return userDetailsDTO;
        }
        return null;
    }

    // 获取解析token密钥
    public SecretKey generalKey() {
        byte[] encodedKey = Base64.getDecoder().decode(SECRET);
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
    }

    public String getUuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
