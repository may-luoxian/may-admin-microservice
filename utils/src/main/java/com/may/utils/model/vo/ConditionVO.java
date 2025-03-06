package com.may.utils.model.vo;

import io.swagger.annotations.ApiModelProperty;

public class ConditionVO {
    @ApiModelProperty(name = "current", value = "页码", dataType = "Long")
    private Long current;

    @ApiModelProperty(name = "size", value = "条数", dataType = "Long")
    private Long size;

    @ApiModelProperty(name = "keywords", value = "查询关键字", dataType = "String")
    private String keywords;

    @ApiModelProperty(name = "type", value = "类型", dataType = "Integer")
    private Integer type;

    public ConditionVO() {
    }

    public ConditionVO(Long current, Long size, String keywords) {
        this.current = current;
        this.size = size;
        this.keywords = keywords;
    }

    public Long getCurrent() {
        return current;
    }

    public void setCurrent(Long current) {
        this.current = current;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
