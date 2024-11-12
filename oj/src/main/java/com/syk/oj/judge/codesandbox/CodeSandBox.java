package com.syk.oj.judge.codesandbox;

import com.syk.oj.judge.codesandbox.model.ExecuteCodeRequest;
import com.syk.oj.judge.codesandbox.model.ExecuteCodeResponse;

/**
 * 代码沙箱接口定义
 */
public interface CodeSandBox {
    /**
     * 执行代码
     * @param executeCodeRequest
     * @return
     */
    ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest);
}
