package com.may.utils.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDateTime;
import java.util.List;

public class UserDetailsShareDTO {
    private Integer id;

    private Integer userInfoId;

    private String email;

    private Integer loginType;

    private String username;

    private String password;

    private List<String> roles;

    private String nickname;

    private String avatar;

    private String intro;

    private String website;

    private Integer isSubscribe;

    private String ipAddress;

    private String ipSource;

    private Integer isDisable;

    private String browser;

    private String os;

    private String token;

    private LocalDateTime expireTime;

    private LocalDateTime lastLoginTime;

    public UserDetailsShareDTO(Integer id, Integer userInfoId, String email, Integer loginType, String username, String password, List<String> roles, String nickname, String avatar, String intro, String website, Integer isSubscribe, String ipAddress, String ipSource, Integer isDisable, String browser, String os, String token, LocalDateTime expireTime, LocalDateTime lastLoginTime) {
        this.id = id;
        this.userInfoId = userInfoId;
        this.email = email;
        this.loginType = loginType;
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.nickname = nickname;
        this.avatar = avatar;
        this.intro = intro;
        this.website = website;
        this.isSubscribe = isSubscribe;
        this.ipAddress = ipAddress;
        this.ipSource = ipSource;
        this.isDisable = isDisable;
        this.browser = browser;
        this.os = os;
        this.token = token;
        this.expireTime = expireTime;
        this.lastLoginTime = lastLoginTime;
    }

    public UserDetailsShareDTO() {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
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

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
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

    public Integer getIsDisable() {
        return isDisable;
    }

    public void setIsDisable(Integer isDisable) {
        this.isDisable = isDisable;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(LocalDateTime expireTime) {
        this.expireTime = expireTime;
    }

    public LocalDateTime getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(LocalDateTime lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }
}
