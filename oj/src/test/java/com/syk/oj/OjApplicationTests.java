package com.syk.oj;

import com.syk.oj.enums.QuestionSubStateEnum;
import com.syk.oj.enums.QuestionSubmitLanguageEnum;
import com.syk.oj.judge.codesandbox.CodeSandBox;
import com.syk.oj.judge.codesandbox.CodeSandBoxFactory;
import com.syk.oj.judge.codesandbox.CodeSandboxProxy;
import com.syk.oj.judge.codesandbox.model.ExecuteCodeRequest;
import com.syk.oj.judge.codesandbox.model.ExecuteCodeResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
class OjApplicationTests {

    @Value("${codeSandBox.type}")
    private String type;
    @Test
    void contextLoads() {
        CodeSandBox codeSandBox = CodeSandBoxFactory.newInstance(type);
        CodeSandboxProxy codeSandboxProxy = new CodeSandboxProxy(codeSandBox);
        String code = "public class Main {\n" +
                "    public static void main(String[] args) {\n" +
                "        int i = Integer.parseInt(args[0]);\n" +
                "        int j = Integer.parseInt(args[1]);\n" +
                "\n" +
                "        System.out.println((i+j));\n" +
                "    }\n" +
                "}";
        ExecuteCodeRequest executeCodeRequest = new ExecuteCodeRequest();
        executeCodeRequest.setCode(code);

        List<String> strings = Arrays.asList("1 3", "2 4");
        executeCodeRequest.setInputList(strings);
        executeCodeRequest.setLanguage(QuestionSubmitLanguageEnum.JAVA.getValue());
        ExecuteCodeResponse executeCodeResponse = codeSandboxProxy.executeCode(executeCodeRequest);
        Assertions.assertNotNull(executeCodeResponse);
    }

}
