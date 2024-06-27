package com.may.auth.model.dto;

import java.time.LocalDateTime;

public class UserInfoDTO {
    private Integer id;

    private Integer userInfoId;

    private String email;

    private Integer loginType;

    private String username;

    private String nickname;

    private String avatar;

    private String website;

    private Integer isSubscribe;

    private String ipAddress;

    private String ipSource;

    private LocalDateTime lastLoginTime;

    private String token;

    public UserInfoDTO() {
    }

    public UserInfoDTO(Integer id, Integer userInfoId, String email, Integer loginType, String username, String nickname, String avatar, String website, Integer isSubscribe, String ipAddress, String ipSource, LocalDateTime lastLoginTime, String token) {
        this.id = id;
        this.userInfoId = userInfoId;
        this.email = email;
        this.loginType = loginType;
        this.username = username;
        this.nickname = nickname;
        this.avatar = avatar;
        this.website = website;
        this.isSubscribe = isSubscribe;
        this.ipAddress = ipAddress;
        this.ipSource = ipSource;
        this.lastLoginTime = lastLoginTime;
        this.token = token;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getLoginType() {
        return loginType;
    }

    public void setLoginType(Integer loginType) {
        this.loginType = loginType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Integer getIsSubscribe() {
        return isSubscribe;
    }

    public void setIsSubscribe(Integer isSubscribe) {
        this.isSubscribe = isSubscribe;
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

    public LocalDateTime getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(LocalDateTime lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
