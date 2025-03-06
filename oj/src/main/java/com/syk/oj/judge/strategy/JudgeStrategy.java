package com.syk.oj.judge.strategy;


import com.syk.oj.judge.codesandbox.model.JudgeInfo;

/**
 * 判题策略
 */
public interface JudgeStrategy {
    /**
     * 执行判题
     * @param judgeContext
     * @return
     */
    JudgeInfo doJudge(JudgeContext judgeContext);

    JudgeInfo doDebug(JudgeContext judgeContext);
}
