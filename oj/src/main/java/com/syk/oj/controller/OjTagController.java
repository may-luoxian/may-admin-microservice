package com.syk.oj.controller;

import com.may.utils.model.vo.ResultVO;
import com.syk.oj.entity.OjTag;
import com.syk.oj.service.OjTagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "oj模块-标签")
@RestController
@RequestMapping("/tag")
public class OjTagController {
    @Autowired
    private OjTagService ojTagService;

    @ApiOperation("获取标签列表")
    @GetMapping("/")
    public ResultVO<List<OjTag>> selectTagList() {
        List<OjTag> list = ojTagService.list();
        return ResultVO.ok(list);
    }
}
