package com.syk.oj.judge.codesandbox.impl;


import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.may.utils.enums.StatusCodeEnum;
import com.may.utils.exception.BizException;
import com.syk.oj.enums.QuestionSubStateEnum;
import com.syk.oj.judge.codesandbox.CodeSandBox;
import com.syk.oj.judge.codesandbox.model.ExecuteCodeRequest;
import com.syk.oj.judge.codesandbox.model.ExecuteCodeResponse;

/**
 * 远程调用代码沙箱
 */
public class RemoteCodeSandBox implements CodeSandBox {

    private static final String URL = "http://localhost:8200/executeCode";

    public static final String AUTH_REQUEST_HEADER = "auth";

    public static final String AUTH_REQUEST_SECRET = "secretKey";

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("远程代码沙箱");
        String json = JSONUtil.toJsonStr(executeCodeRequest);
            String response = HttpUtil
                    .createPost(URL)
                    .header(AUTH_REQUEST_HEADER, AUTH_REQUEST_SECRET)
                    .body(json)
                    .execute()
                    .body();
            if (StringUtils.isBlank(response)) {
                throw new BizException(StatusCodeEnum.API_REQUEST_ERROR.getCode(), "执行远程代码沙箱错误，message = " + response);
            }
            return JSONUtil.toBean(response, ExecuteCodeResponse.class);
    }
}
