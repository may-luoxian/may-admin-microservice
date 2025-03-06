package com.syk.oj.judge.strategy.impl;

import cn.hutool.json.JSONUtil;
import com.syk.oj.entity.OjQuestion;
import com.syk.oj.enums.JudgeInfoMsgEnum;
import com.syk.oj.judge.codesandbox.model.JudgeInfo;
import com.syk.oj.judge.strategy.JudgeContext;
import com.syk.oj.judge.strategy.JudgeStrategy;
import com.syk.oj.model.dto.JudgeCaseDTO;
import com.syk.oj.model.dto.JudgeConfigDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 默认判题策略
 */
public class DefaultJudgeStrategy implements JudgeStrategy {
    @Override
    public JudgeInfo doJudge(JudgeContext judgeContext) {
        JudgeInfo judgeInfo = judgeContext.getJudgeInfo();
        List<String> inputList = judgeContext.getInputList();
        List<String> outputList = judgeContext.getOutputList();
        OjQuestion ojQuestion = judgeContext.getOjQuestion();
        List<JudgeCaseDTO> judgeCaseDTOList = judgeContext.getJudgeCaseDTOList();
        Long realMemory = Optional.ofNullable(judgeInfo.getMemory()).orElse(0L);
        Long realTime = Optional.ofNullable(judgeInfo.getTime()).orElse(0L);

        JudgeInfo judgeInfoResponse = new JudgeInfo();
        judgeInfoResponse.setMemory(realMemory);
        judgeInfoResponse.setTime(realTime);

        JudgeInfoMsgEnum judgeInfoMsgEnum = JudgeInfoMsgEnum.ACCEPTED;

        // 1. 判断沙箱代码执行状态 1：代码运行成功 2：编译失败 3：运行失败
        Integer sandboxState = judgeContext.getSandboxState();

        if (sandboxState == 2) { // 编译错误
            judgeInfoResponse.setMessage(judgeInfoMsgEnum.COMPILE_ERROR.getMessage());
            return judgeInfoResponse;
        }

        if (sandboxState == 3) { // 运行失败
            judgeInfoResponse.setInputList(inputList);
            judgeInfoResponse.setOutputList(outputList);
            judgeInfoResponse.setMessage(judgeInfoMsgEnum.RUNTIME_ERROR.getMessage());
            return judgeInfoResponse;
        }

        // 代码运行成功（输入和输出数量一致）
        // 依次对比每个输出与题目预期答案是否一致
        judgeInfoResponse.setInputList(inputList);
        judgeInfoResponse.setOutputList(outputList);
        for (int i = 0; i < judgeCaseDTOList.size(); i++) {
            JudgeCaseDTO judgeCaseDTO = judgeCaseDTOList.get(i);
            if (!judgeCaseDTO.getOutput().equals(outputList.get(i))) {
                judgeInfoMsgEnum = JudgeInfoMsgEnum.WRONG_ANSWER;
                judgeInfoResponse.setMessage(judgeInfoMsgEnum.getMessage());
            }
        }

        String judgeConfig = ojQuestion.getJudgeConfig();
        JudgeConfigDTO judgeConfigDTO = JSONUtil.toBean(judgeConfig, JudgeConfigDTO.class);
        Long memoryLimit = judgeConfigDTO.getMemoryLimit();
        Long timeLimit = judgeConfigDTO.getTimeLimit();
        // 对比内存、时间是否超出规定
        if ((realMemory / 1024) > memoryLimit) {
            judgeInfoMsgEnum = JudgeInfoMsgEnum.MEMORY_LIMIT_EXCEEDED;
            judgeInfoResponse.setMessage(judgeInfoMsgEnum.getMessage());
            return judgeInfoResponse;
        }

        if (realTime > timeLimit) {
            judgeInfoMsgEnum = JudgeInfoMsgEnum.TIME_LIMIT_EXCEEDED;
            judgeInfoResponse.setMessage(judgeInfoMsgEnum.getMessage());
            return judgeInfoResponse;
        }
        judgeInfoResponse.setMessage(judgeInfoMsgEnum.getMessage());
        return judgeInfoResponse;
    }

    @Override
    public JudgeInfo doDebug(JudgeContext judgeContext) {
        // 1. 判断沙箱代码执行状态 1：代码运行成功 2：编译失败 3：运行失败
        Integer sandboxState = judgeContext.getSandboxState();
        List<String> inputList = judgeContext.getInputList();
        List<String> outputList = judgeContext.getOutputList();
        String correctAnswer = judgeContext.getCorrectAnswer();

        JudgeInfo judgeInfoResponse = new JudgeInfo();
        JudgeInfoMsgEnum judgeInfoMsgEnum = JudgeInfoMsgEnum.ACCEPTED;
        if (sandboxState == 2) { // 编译错误
            judgeInfoResponse.setMessage(judgeInfoMsgEnum.COMPILE_ERROR.getMessage());
            return judgeInfoResponse;
        }

        if (sandboxState == 3) { // 运行失败
            judgeInfoResponse.setInputList(inputList);
            judgeInfoResponse.setOutputList(outputList);
            judgeInfoResponse.setMessage(judgeInfoMsgEnum.RUNTIME_ERROR.getMessage());
            return judgeInfoResponse;
        }

        // 代码运行成功（输入和输出数量一致）
        // 对比两个沙箱运行结果是否一致
        String output = outputList.get(0);
        if (!correctAnswer.equals(output)) {
            judgeInfoMsgEnum = JudgeInfoMsgEnum.WRONG_ANSWER;
            judgeInfoResponse.setMessage(judgeInfoMsgEnum.getMessage());
        }

        judgeInfoResponse.setMessage(judgeInfoMsgEnum.getMessage());
        return judgeInfoResponse;
    }
}
