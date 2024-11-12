package com.syk.oj.judge;

import com.syk.oj.entity.OjQuestionSubmit;
import com.syk.oj.enums.QuestionSubmitLanguageEnum;
import com.syk.oj.judge.codesandbox.model.JudgeInfo;
import com.syk.oj.judge.strategy.JudgeContext;
import com.syk.oj.judge.strategy.JudgeStrategy;
import com.syk.oj.judge.strategy.impl.DefaultJudgeStrategy;
import org.springframework.stereotype.Service;

/**
 * 判题管理（简化调用）
 */
@Service
public class JudgeManager {
    public JudgeInfo doJudge(JudgeContext judgeContext) {
        OjQuestionSubmit ojQuestionSubmit = judgeContext.getOjQuestionSubmit();
        String language = ojQuestionSubmit.getLanguage();
        JudgeStrategy judgeStrategy = new DefaultJudgeStrategy();
        if (QuestionSubmitLanguageEnum.JAVA.getValue().equals(language)) {
            judgeStrategy = new DefaultJudgeStrategy();
        }
        return judgeStrategy.doJudge(judgeContext);
    }
}
