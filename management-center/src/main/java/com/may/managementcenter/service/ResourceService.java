package com.may.managementcenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.may.managementcenter.entity.Resource;
import com.may.managementcenter.model.dto.LabelOptionDTO;
import com.may.managementcenter.model.dto.ResourceDTO;
import com.may.managementcenter.model.vo.ResourceVO;

import java.util.List;

public interface ResourceService extends IService<Resource> {
    List<LabelOptionDTO> listRoleResources(ResourceVO resourceVO);

    List<ResourceDTO> listResources(ResourceVO resourceVO);

    void saveOrUpdateResource(ResourceVO resourceVO);
    void deleteResources(List<Integer> ids);
}
