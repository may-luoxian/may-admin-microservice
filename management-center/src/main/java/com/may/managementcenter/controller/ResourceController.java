package com.may.managementcenter.controller;

import com.may.managementcenter.model.dto.ResourceDTO;
import com.may.managementcenter.model.vo.ResourceVO;
import com.may.managementcenter.service.ResourceService;
import com.may.utils.model.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "资源模块")
@RestController
@RequestMapping("/admin/resource")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @ApiOperation(value = "获取资源列表")
    @GetMapping("/resources")
    public ResultVO<List<ResourceDTO>> listResources(ResourceVO resourceVO) {
        return ResultVO.ok(resourceService.listResources(resourceVO));
    }

    @ApiOperation(value = "新增或修改资源")
    @PostMapping("/resources")
    public ResultVO<?> saveOrUpdateResource(@RequestBody ResourceVO resourceVO) {
        resourceService.saveOrUpdateResource(resourceVO);
        return ResultVO.ok();
    }

    @ApiOperation(value = "删除资源")
    @DeleteMapping("/resources")
    public ResultVO<?> deleteResources(@RequestBody List<Integer> ids) {
        resourceService.deleteResources(ids);
        return ResultVO.ok();
    }
}
