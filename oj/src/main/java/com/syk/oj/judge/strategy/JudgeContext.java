package com.syk.oj.judge.strategy;

import com.syk.oj.entity.OjQuestion;
import com.syk.oj.entity.OjQuestionSubmit;
import com.syk.oj.judge.codesandbox.model.JudgeInfo;
import com.syk.oj.model.dto.JudgeCaseDTO;
import lombok.Data;

import java.util.List;

/**
 * 策略上下文（用于定义在策略中传递的参数）
 */
@Data
public class JudgeContext {
    private JudgeInfo judgeInfo;

    private List<String> inputList;

    private List<String> outputList;

    private List<JudgeCaseDTO> judgeCaseDTOList;

    private OjQuestion ojQuestion;

    private OjQuestionSubmit ojQuestionSubmit;
}
