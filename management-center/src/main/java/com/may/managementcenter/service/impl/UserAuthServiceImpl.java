package com.may.managementcenter.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.may.managementcenter.entity.UserAuth;
import com.may.managementcenter.entity.UserHome;
import com.may.managementcenter.entity.UserInfo;
import com.may.managementcenter.entity.UserRole;
import com.may.managementcenter.mapper.UserAuthMapper;
import com.may.managementcenter.mapper.UserHomeMapper;
import com.may.managementcenter.mapper.UserInfoMapper;
import com.may.managementcenter.mapper.UserRoleMapper;
import com.may.managementcenter.model.dto.UserAreaDTO;
import com.may.managementcenter.model.dto.UserDetailsDTO;
import com.may.managementcenter.model.dto.UserInfoDTO;
import com.may.managementcenter.service.UserAuthService;
import com.may.utils.Bean.BeanCopyUtil;
import com.may.utils.exception.BizException;
import com.may.utils.feignapi.RedisClient;
import com.may.utils.getuserdetails.UserInfoContext;
import com.may.utils.model.dto.RedisDelDTO;
import com.may.utils.model.vo.ConditionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.may.utils.constant.RedisConstant.*;
import static com.may.utils.enums.UserAreaTypeEnum.getUserAreaType;

@Service
public class UserAuthServiceImpl extends ServiceImpl<UserAuthMapper, UserAuth> implements UserAuthService {

    @Autowired
    private UserAuthMapper userAuthMapper;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private UserHomeMapper userHomeMapper;

    @Autowired
    private RedisClient redisClient;

    @Override
    public void logout(Integer id) {
        RedisDelDTO redisDelDTO = new RedisDelDTO();
        redisDelDTO.setKey(LOGIN_USER);
        ArrayList<Object> objects = new ArrayList<>();
        objects.add(String.valueOf(id));
        redisDelDTO.setHashKey(objects);
        redisClient.hDel(redisDelDTO);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createUser(UserDetailsDTO userDetailsDTO) {
        if (checkUser(userDetailsDTO)) {
            throw new BizException("该账号已被注册");
        }
        UserInfo userInfo = UserInfo.builder()
                .email(userDetailsDTO.getEmail())
                .nickname(userDetailsDTO.getNickname())
                .avatar(userDetailsDTO.getAvatar())
                .intro(userDetailsDTO.getIntro())
                .website(userDetailsDTO.getWebsite())
                .isSubscribe(userDetailsDTO.getIsSubscribe())
                .isDisable(userDetailsDTO.getIsDisable())
                .createTime(LocalDateTime.now())
                .build();
        userInfoMapper.insertUserInfo(userInfo);
        UserAuth userAuth = UserAuth.builder()
                .userInfoId(userInfo.getId())
                .username(userDetailsDTO.getUsername())
                .password(BCrypt.hashpw(userDetailsDTO.getPassword(), BCrypt.gensalt()))
                .loginType(userDetailsDTO.getLoginType())
                .build();
        userAuthMapper.insert(userAuth);
    }

    @Override
    public List<UserAreaDTO> listUserAreas(ConditionVO conditionVO) {
        List<UserAreaDTO> userAreaDTOs = new ArrayList<>();
        switch (Objects.requireNonNull(getUserAreaType(conditionVO.getType()))) {
            case USER:
                Object userArea = redisClient.get(USER_AREA);
                if (Objects.nonNull(userArea)) {
                    userAreaDTOs = JSON.parseObject(userArea.toString(), List.class);
                }
                return userAreaDTOs;
            case VISITOR:
                Map<String, Object> visitorArea = redisClient.hGetAll(VISITOR_AREA);
                if (Objects.nonNull(visitorArea)) {
                    userAreaDTOs = visitorArea.entrySet().stream()
                            .map(item -> UserAreaDTO.builder()
                                    .name(item.getKey())
                                    .value(Long.valueOf(item.getValue().toString()))
                                    .build())
                            .collect(Collectors.toList());
                }
                return userAreaDTOs;
            default:
                break;
        }
        return userAreaDTOs;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteUser(Integer id) {
        UserAuth userAuth = userAuthMapper.selectOne(new LambdaQueryWrapper<UserAuth>()
                .select(UserAuth::getUserInfoId)
                .eq(UserAuth::getId, id));
        Integer userInfoId = userAuth.getUserInfoId();
        userRoleMapper.delete(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, userInfoId));
        userInfoMapper.delete(new LambdaQueryWrapper<UserInfo>().eq(UserInfo::getId, userInfoId));
        userAuthMapper.delete(new LambdaQueryWrapper<UserAuth>().eq(UserAuth::getId, id));
        userHomeMapper.delete(new LambdaQueryWrapper<UserHome>().eq(UserHome::getUserInfoId, userInfoId));
    }

    @Override
    public UserInfoDTO userInfo() {
        UserInfoDTO userInfoDTO = BeanCopyUtil.copyObject(UserInfoContext.getUser(), UserInfoDTO.class);
        return userInfoDTO;
    }

    private Boolean checkUser(UserDetailsDTO userDetailsDTO) {
        UserAuth userAuth = userAuthMapper.selectOne(new LambdaQueryWrapper<UserAuth>()
                .select(UserAuth::getUsername)
                .eq(UserAuth::getUsername, userDetailsDTO.getUsername()));
        return Objects.nonNull(userAuth);
    }
}
