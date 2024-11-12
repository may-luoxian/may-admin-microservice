package com.may.managementcenter.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class ResourceRoleDTO {
    private Integer id;

    private String url;

    private String requestMethod;

    private List<String> roleList;
}
