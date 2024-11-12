package com.syk.oj.judge;


import com.syk.oj.entity.OjQuestionSubmit;

/**
 * 判题服务
 */
public interface JudgeService {
    /**
     * 判题
     * @param questionSubmitId
     * @return
     */
    OjQuestionSubmit doJudge(Integer questionSubmitId);
}
