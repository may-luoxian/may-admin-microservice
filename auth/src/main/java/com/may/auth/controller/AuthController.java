package com.may.auth.controller;

import com.alibaba.fastjson.JSON;
import com.may.auth.handler.FilterInvocationSecurityMetadataSourceImpl;
import com.may.utils.model.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class AuthController {
    @Autowired
    private FilterInvocationSecurityMetadataSourceImpl filterInvocationSecurityMetadataSource;

    @PostMapping("/validateResource")
    public void validateResources(HttpServletResponse response) throws IOException {
        response.getWriter().write(JSON.toJSONString(ResultVO.ok()));
    }

    @GetMapping("/clearResource")
    public void clearResource() {
        filterInvocationSecurityMetadataSource.clearDataSource();
    }
}
