package com.syk.oj.judge;


import com.syk.oj.entity.OjQuestionSubmit;
import com.syk.oj.model.dto.DebugDetailDTO;
import com.syk.oj.model.vo.OjQuestionSubmitVO;

/**
 * 判题服务
 */
public interface JudgeService {
    /**
     * 判题
     * @param questionSubmitId
     * @return
     */
    void doJudge(Integer questionSubmitId);

    /**
     * debug
     */
    DebugDetailDTO doDebug(OjQuestionSubmitVO ojQuestionSubmitVO);
}
