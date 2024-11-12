package com.syk.oj.judge.codesandbox.impl;


import com.syk.oj.judge.codesandbox.CodeSandBox;
import com.syk.oj.judge.codesandbox.model.ExecuteCodeRequest;
import com.syk.oj.judge.codesandbox.model.ExecuteCodeResponse;

/**
 * 远程调用代码沙箱
 */
public class RemoteCodeSandBox implements CodeSandBox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("远程代码沙箱");
        return null;
    }
}
