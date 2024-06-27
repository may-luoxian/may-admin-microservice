package com.may.auth.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class AuthController {
    @PostMapping("/validateResource")
    public void validateResources(HttpServletResponse response) throws IOException {
        response.getWriter().write("继续转发");
    }
}
