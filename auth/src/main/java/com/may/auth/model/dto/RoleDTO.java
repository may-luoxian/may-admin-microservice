package com.may.auth.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;

public class RoleDTO {
    private Integer id;

    private String roleName;

    private String describe;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    private Integer isDisable;

    private List<Integer> resourceIds;

    private List<Integer> menuIds;

    public RoleDTO() {
    }

    public RoleDTO(Integer id, String roleName, String describe, LocalDateTime createTime, Integer isDisable, List<Integer> resourceIds, List<Integer> menuIds) {
        this.id = id;
        this.roleName = roleName;
        this.describe = describe;
        this.createTime = createTime;
        this.isDisable = isDisable;
        this.resourceIds = resourceIds;
        this.menuIds = menuIds;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public Integer getIsDisable() {
        return isDisable;
    }

    public void setIsDisable(Integer isDisable) {
        this.isDisable = isDisable;
    }

    public List<Integer> getResourceIds() {
        return resourceIds;
    }

    public void setResourceIds(List<Integer> resourceIds) {
        this.resourceIds = resourceIds;
    }

    public List<Integer> getMenuIds() {
        return menuIds;
    }

    public void setMenuIds(List<Integer> menuIds) {
        this.menuIds = menuIds;
    }
}
