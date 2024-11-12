package com.may.managementcenter.model.dto;

import java.util.List;

public class UserMenuDTO {
    public UserMenuDTO() {
    }

    public UserMenuDTO(String path, String name, Integer id, Integer menuType, String component, String icon, Integer isHidden, Integer parentId, Integer orderNum, List<UserMenuDTO> children) {
        this.path = path;
        this.name = name;
        this.id = id;
        this.menuType = menuType;
        this.component = component;
        this.icon = icon;
        this.isHidden = isHidden;
        this.parentId = parentId;
        this.orderNum = orderNum;
        this.children = children;
    }

    private String path;

        private String name;

        private Integer id;

        private Integer menuType;

        private String component;

        private String icon;

        private Integer isHidden;

        private Integer parentId;

        private Integer orderNum;

        private List<UserMenuDTO> children;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMenuType() {
        return menuType;
    }

    public void setMenuType(Integer menuType) {
        this.menuType = menuType;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getIsHidden() {
        return isHidden;
    }

    public void setIsHidden(Integer isHidden) {
        this.isHidden = isHidden;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public List<UserMenuDTO> getChildren() {
        return children;
    }

    public void setChildren(List<UserMenuDTO> children) {
        this.children = children;
    }
}
