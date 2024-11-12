package com.syk.oj.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.may.utils.Bean.BeanCopyUtil;
import com.may.utils.exception.BizException;
import com.may.utils.getuserdetails.UserInfoContext;
import com.syk.oj.entity.OjQuestion;
import com.syk.oj.entity.OjQuestionSubmit;
import com.syk.oj.enums.QuestionSubStateEnum;
import com.syk.oj.judge.JudgeService;
import com.syk.oj.judge.codesandbox.model.JudgeInfo;
import com.syk.oj.mapper.OjQuestionSubmitMapper;
import com.syk.oj.model.vo.OjQuestionSubmitVO;
import com.syk.oj.service.OjQuestionService;
import com.syk.oj.service.OjQuestionSubmitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

/**
 * @author sunyukun
 * @description 针对表【oj_question_submit】的数据库操作Service实现
 * @createDate 2024-01-28 06:19:53
 */
@Service
public class OjQuestionSubmitServiceImpl extends ServiceImpl<OjQuestionSubmitMapper, OjQuestionSubmit>
        implements OjQuestionSubmitService {

    @Autowired
    private OjQuestionService questionService;

    @Autowired
    private JudgeService judgeService;

    @Override
    public Integer questionSubmit(OjQuestionSubmitVO questionSubmitVO) {
        OjQuestion question = questionService.getById(questionSubmitVO.getQuestionId());
        if (question == null) {
            throw new BizException("题目不存在");
        }
        // 题目提交初始化操作
        Integer userInfoId = UserInfoContext.getUser().getUserInfoId();
        OjQuestionSubmit questionSubmit = BeanCopyUtil.copyObject(questionSubmitVO, OjQuestionSubmit.class);
        questionSubmit.setUserInfoId(userInfoId);
        questionSubmit.setStatus(QuestionSubStateEnum.UNTREATED.getValue());
        JudgeInfo judgeInfo = new JudgeInfo();
        questionSubmit.setJudgeInfo(JSONUtil.toJsonStr(judgeInfo));
        boolean save = this.save(questionSubmit);
        if (!save) {
            throw new BizException("题目提交失败");
        }
        // TODO: 执行判题服务
        Integer questionSubmitId = questionSubmit.getId();
        CompletableFuture.runAsync(() -> {
            judgeService.doJudge(questionSubmitId);
        });
        return questionSubmitId;
    }
}
