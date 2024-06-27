package com.may.auth.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;

@TableName("t_user_auth")
public class UserAuth {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer userInfoId;

    private String username;

    private String password;

    private Integer loginType;

    private String ipAddress;

    private String ipSource;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

    private LocalDateTime lastLoginTime;

    public UserAuth() {
    }

    public UserAuth(Integer id, Integer userInfoId, String username, String password, Integer loginType, String ipAddress, String ipSource, LocalDateTime createTime, LocalDateTime updateTime, LocalDateTime lastLoginTime) {
        this.id = id;
        this.userInfoId = userInfoId;
        this.username = username;
        this.password = password;
        this.loginType = loginType;
        this.ipAddress = ipAddress;
        this.ipSource = ipSource;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.lastLoginTime = lastLoginTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserInfoId() {
        return userInfoId;
    }

    public void setUserInfoId(Integer userInfoId) {
        this.userInfoId = userInfoId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getLoginType() {
        return loginType;
    }

    public void setLoginType(Integer loginType) {
        this.loginType = loginType;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getIpSource() {
        return ipSource;
    }

    public void setIpSource(String ipSource) {
        this.ipSource = ipSource;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public LocalDateTime getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(LocalDateTime lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }
}
