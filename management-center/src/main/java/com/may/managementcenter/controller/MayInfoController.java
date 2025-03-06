package com.may.managementcenter.controller;

import com.may.managementcenter.service.MayInfoService;
import com.may.utils.model.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "may信息")
@RestController
@RequestMapping("/info")
public class MayInfoController {

    @Autowired
    private MayInfoService mayInfoService;


    @ApiOperation(value = "上报访客信息")
    @PostMapping("/report")
    public ResultVO<?> report() {
        mayInfoService.report();
        return ResultVO.ok();
    }
}
