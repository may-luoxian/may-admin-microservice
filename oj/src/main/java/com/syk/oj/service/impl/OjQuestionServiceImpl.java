package com.syk.oj.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.may.utils.Bean.BeanCopyUtil;
import com.syk.oj.entity.OjQuestion;
import com.syk.oj.mapper.OjQuestionMapper;
import com.syk.oj.model.dto.JudgeCaseDTO;
import com.syk.oj.model.dto.JudgeConfigDTO;
import com.syk.oj.model.dto.OjQuestionDTO;
import com.syk.oj.service.OjQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.alibaba.fastjson.JSON.toJSONString;

/**
 * @author sunyukun
 * @description 针对表【oj_question(oj模块-题目)】的数据库操作Service实现
 * @createDate 2024-01-28 06:17:29
 */
@Service
public class OjQuestionServiceImpl extends ServiceImpl<OjQuestionMapper, OjQuestion> implements OjQuestionService {
    @Autowired
    private OjQuestionMapper ojQuestionMapper;

    @Override
    public void validateQuestion(OjQuestion ojQuestion, boolean add) {

    }

    @Override
    public OjQuestion copyQuestion(OjQuestionDTO ojQuestionDTO) {
        OjQuestion ojQuestion = BeanCopyUtil.copyObject(ojQuestionDTO, OjQuestion.class);
        List<String> tags = ojQuestionDTO.getTags();
        if (Objects.nonNull(tags)) {
            ojQuestion.setTags(toJSONString(tags));
        }
        List<JudgeCaseDTO> judgeCases = ojQuestionDTO.getJudgeCase();
        if (Objects.nonNull(judgeCases)) {
            ojQuestion.setJudgeCase(toJSONString(judgeCases));
        }
        JudgeConfigDTO judgeConfig = ojQuestionDTO.getJudgeConfig();
        if (Objects.nonNull(judgeConfig)) {
            ojQuestion.setJudgeConfig(toJSONString(judgeConfig));
        }
        return ojQuestion;
    }

    @Override
    public OjQuestionDTO copyOjQuestionDTO(OjQuestion ojQuestion) {
        OjQuestionDTO ojQuestionDTO = BeanCopyUtil.copyObject(ojQuestion, OjQuestionDTO.class);
        String tagstring = ojQuestion.getTags();
        if (StringUtils.isNotBlank(tagstring)) {
            List<String> tags = JSON.parseArray(tagstring, String.class);
            ojQuestionDTO.setTags(tags);
        }
        String judgeCaseString = ojQuestion.getJudgeCase();
        if (StringUtils.isNotBlank(judgeCaseString)) {
            List<JudgeCaseDTO> judgeCase = JSON.parseArray(judgeCaseString, JudgeCaseDTO.class);
            ojQuestionDTO.setJudgeCase(judgeCase);
        }
        String judgeConfigString = ojQuestion.getJudgeConfig();
        if (StringUtils.isNotBlank(judgeConfigString)) {
            JudgeConfigDTO judgeConfig = JSON.parseObject(judgeConfigString, JudgeConfigDTO.class);
            ojQuestionDTO.setJudgeConfig(judgeConfig);
        }
        return ojQuestionDTO;
    }

    @Override
    public List<OjQuestionDTO> selectQuestionList() {
        List<OjQuestion> ojQuestions = ojQuestionMapper.selectQuestionList();
        ArrayList<OjQuestionDTO> ojQuestionDTOS = new ArrayList<>();
        ojQuestions.stream().forEach(item -> {
            OjQuestionDTO ojQuestionDTO = this.copyOjQuestionDTO(item);
            ojQuestionDTOS.add(ojQuestionDTO);
        });
        return ojQuestionDTOS;
    }

    @Override
    public OjQuestionDTO selectQuestionById(Integer id) {
        OjQuestion ojQuestion = ojQuestionMapper.selectQuestionById(id);
        return this.copyOjQuestionDTO(ojQuestion);
    }
}




