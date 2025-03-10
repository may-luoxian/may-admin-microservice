package com.syk.oj.judge.codesandbox;


import com.syk.oj.judge.codesandbox.impl.ExampleCodeSandBox;
import com.syk.oj.judge.codesandbox.impl.RemoteCodeSandBox;
import com.syk.oj.judge.codesandbox.impl.ThirdPartyCodeSandBox;

/**
 * ATTENTION: 工厂模式
 * 代码沙箱工厂（根据字符串参数创建指定的代码沙箱实例）
 */
public class CodeSandBoxFactory {
    public static CodeSandBox newInstance(String type) {
        switch (type) {
            case "example":
                return new ExampleCodeSandBox();
            case "remote":
                return new RemoteCodeSandBox();
            case "thirdParty":
                return new ThirdPartyCodeSandBox();
            default:
                return new ExampleCodeSandBox();
        }
    }
}
