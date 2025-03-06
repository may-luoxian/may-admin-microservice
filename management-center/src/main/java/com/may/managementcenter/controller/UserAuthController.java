package com.may.managementcenter.controller;

import com.may.managementcenter.model.dto.UserAdminDTO;
import com.may.managementcenter.model.dto.UserAreaDTO;
import com.may.managementcenter.model.dto.UserDetailsDTO;
import com.may.managementcenter.model.dto.UserInfoDTO;
import com.may.managementcenter.model.vo.UserConditionVO;
import com.may.managementcenter.service.UserAuthService;
import com.may.managementcenter.service.UserInfoService;
import com.may.utils.model.dto.PageResultDTO;
import com.may.utils.model.vo.ConditionVO;
import com.may.utils.model.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api(tags = "用户账号模块")
@RestController
@RequestMapping("/users")
public class UserAuthController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private UserAuthService userAuthService;

    @ApiOperation("获取用户列表")
    @GetMapping("/list")
    public PageResultDTO<UserAdminDTO> listUsers(UserConditionVO userConditionVO) {
        return userInfoService.selectUserInfoList(userConditionVO);
    }

    @ApiOperation(value = "获取用户区域分布")
    @GetMapping("/area")
    public ResultVO<List<UserAreaDTO>> listUserAreas(ConditionVO conditionVO) {
        return ResultVO.ok(userAuthService.listUserAreas(conditionVO));
    }

    @ApiOperation("创建用户")
    @PostMapping("/user")
    public ResultVO<?> createUser(@RequestBody UserDetailsDTO userDetailsDTO) {
        userAuthService.createUser(userDetailsDTO);
        return ResultVO.ok();
    }


    @ApiOperation("查询用户信息")
    @GetMapping("/info")
    public ResultVO<UserInfoDTO> getUserInfo() {
        return ResultVO.ok(userAuthService.userInfo());
    }

    @ApiOperation("删除用户")
    @DeleteMapping("/user")
    public ResultVO<?> deleteUser(@RequestBody Map map) {
        userAuthService.deleteUser((Integer) map.get("id"));
        return ResultVO.ok();
    }

    @ApiOperation("退出登录")
    @GetMapping("/logout")
    public ResultVO<?> logout(@RequestParam("userId") Integer id) {
        userAuthService.logout(id);
        return ResultVO.ok();
    }
}
