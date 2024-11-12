package com.syk.oj.judge.codesandbox.impl;


import com.syk.oj.judge.codesandbox.CodeSandBox;
import com.syk.oj.judge.codesandbox.model.ExecuteCodeRequest;
import com.syk.oj.judge.codesandbox.model.ExecuteCodeResponse;

/**
 * 三方调用代码沙箱
 */
public class ThirdPartyCodeSandBox implements CodeSandBox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("第三方代码沙箱");
        return null;
    }
}
