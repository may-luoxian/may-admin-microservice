package com.syk.oj.service.impl;

import cn.hutool.db.Page;
import cn.hutool.db.PageResult;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.may.utils.Bean.BeanCopyUtil;
import com.may.utils.PageUtil;
import com.may.utils.exception.BizException;
import com.may.utils.getuserdetails.UserInfoContext;
import com.may.utils.model.dto.PageResultDTO;
import com.syk.oj.entity.OjQuestion;
import com.syk.oj.entity.OjQuestionSubmit;
import com.syk.oj.enums.QuestionSubStateEnum;
import com.syk.oj.judge.JudgeService;
import com.syk.oj.judge.codesandbox.model.JudgeInfo;
import com.syk.oj.mapper.OjQuestionSubmitMapper;
import com.syk.oj.model.dto.*;
import com.syk.oj.model.vo.OjQuestionSubmitVO;
import com.syk.oj.model.vo.SubmissionRecordVO;
import com.syk.oj.service.OjQuestionService;
import com.syk.oj.service.OjQuestionSubmitService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

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

    @Autowired
    private OjQuestionSubmitMapper ojQuestionSubmitMapper;

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

    @Override
    public DebugDetailDTO questionDebug(OjQuestionSubmitVO questionSubmitVO) {
        OjQuestion question = questionService.getById(questionSubmitVO.getQuestionId());
        if (question == null) {
            throw new BizException("题目不存在");
        }
        DebugDetailDTO debugDetailDTO = judgeService.doDebug(questionSubmitVO);
        return debugDetailDTO;
    }

    @SneakyThrows
    @Override
    public PageResultDTO<SubmissionRecordDTO> querySubmissionRecord(SubmissionRecordVO submissionRecordVO) {
        CompletableFuture<Long> supplyCount = CompletableFuture.supplyAsync(() ->
                ojQuestionSubmitMapper.querySubmissionRecordCount(submissionRecordVO));
        Long limitCurrent = PageUtil.getLimitCurrent();
        List<SubmissionRecordDTO> submissionRecordDTOS = ojQuestionSubmitMapper.querySubmissionRecord(limitCurrent, PageUtil.getSize(), submissionRecordVO);
        submissionRecordDTOS.stream().forEach(submissionRecordDTO -> {
            JudgeInfo judgeInfo = JSONUtil.toBean(submissionRecordDTO.getJudgeInfoJson(), JudgeInfo.class);
            submissionRecordDTO.setJudgeInfo(judgeInfo);
        });
        PageResultDTO pageResultDTO = new PageResultDTO(submissionRecordDTOS, supplyCount.get());
        return pageResultDTO;
    }

    @Override
    public WrongDetailDTO queryWrongDetail(Integer id) {
        OjQuestionSubmit ojQuestionSubmit = ojQuestionSubmitMapper.selectById(id);
        Integer questionId = ojQuestionSubmit.getQuestionId();
        OjQuestionDTO ojQuestionDTO = questionService.selectQuestionById(questionId);
        List<JudgeCaseDTO> judgeCase = ojQuestionDTO.getJudgeCase();
        List<String> expectedList = judgeCase.stream()
                .map(JudgeCaseDTO::getOutput)
                .collect(Collectors.toList());

        JudgeInfo judgeInfo = JSONUtil.toBean(ojQuestionSubmit.getJudgeInfo(), JudgeInfo.class);
        judgeInfo.setExpectedResult(expectedList);
        WrongDetailDTO wrongDetailDTO = BeanCopyUtil.copyObject(ojQuestionSubmit, WrongDetailDTO.class);
        wrongDetailDTO.setJudgeInfo(judgeInfo);
        wrongDetailDTO.setAnswer(ojQuestionDTO.getAnswer());
        return wrongDetailDTO;
    }
}
