package com.may.managementcenter.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ApiModel(description = "菜单")
public class MenuVO {
    @ApiModelProperty(name = "id", value = "菜单id", dataType = "Integer")
    private Integer id;

    @ApiModelProperty(name = "name", value = "菜单名称", dataType = "String")
    @NotBlank(message = "菜单名称不能为空")
    private String name;

    @ApiModelProperty(name = "path", value = "菜单路径", dataType = "String")
    @NotBlank(message = "菜单路径不能为空")
    private String path;

    @ApiModelProperty(name = "component", value = "组件路径", dataType = "String")
    @NotBlank(message = "组件路径不能为空")
    private String component;

    @ApiModelProperty(name = "icon", value = "菜单图标", dataType = "String")
    @NotBlank(message = "菜单图标不能为空")
    private String icon;

    @ApiModelProperty(name = "menuType", value = "菜单类型 0 目录 1菜单", dataType = "Integer")
    @NotNull(message = "菜单类型不能为空")
    private Integer menuType;

    @ApiModelProperty(name = "orderNum", value = "排序", dataType = "Integer")
    @NotNull(message = "排序不能为空")
    private Integer orderNum;

    @ApiModelProperty(name = "parentId", value = "父目录id", dataType = "Integer")
    private Integer parentId;

    @ApiModelProperty(name = "isHidden", value = "是否隐藏 0显示 1隐藏 默认显示", dataType = "Integer")
    private Integer isHidden;

    public MenuVO(Integer id, String name, String path, String component, String icon, Integer menuType, Integer orderNum, Integer parentId, Integer isHidden) {
        this.id = id;
        this.name = name;
        this.path = path;
        this.component = component;
        this.icon = icon;
        this.menuType = menuType;
        this.orderNum = orderNum;
        this.parentId = parentId;
        this.isHidden = isHidden;
    }

    public MenuVO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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

    public Integer getMenuType() {
        return menuType;
    }

    public void setMenuType(Integer menuType) {
        this.menuType = menuType;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getIsHidden() {
        return isHidden;
    }

    public void setIsHidden(Integer isHidden) {
        this.isHidden = isHidden;
    }
}
