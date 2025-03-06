package com.syk.oj.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.may.utils.Bean.BeanCopyUtil;
import com.may.utils.PageUtil;
import com.may.utils.exception.BizException;
import com.may.utils.getuserdetails.UserInfoContext;
import com.may.utils.model.dto.PageResultDTO;
import com.syk.oj.entity.OjQuestion;
import com.syk.oj.entity.OjQuestionTag;
import com.syk.oj.entity.OjTag;
import com.syk.oj.mapper.OjQuestionMapper;
import com.syk.oj.mapper.OjQuestionTagMapper;
import com.syk.oj.mapper.OjTagMapper;
import com.syk.oj.model.dto.JudgeCaseDTO;
import com.syk.oj.model.dto.JudgeConfigDTO;
import com.syk.oj.model.dto.OjQuestionDTO;
import com.syk.oj.model.vo.OjQuestionVO;
import com.syk.oj.service.OjQuestionService;
import com.syk.oj.service.OjQuestionTagService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

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

    @Autowired
    private OjTagMapper ojTagMapper;

    @Autowired
    private OjQuestionTagService ojQuestionTagService;

    @Autowired
    private OjQuestionTagMapper ojQuestionTagMapper;

    @Override
    public void validateQuestion(OjQuestion ojQuestion, boolean add) {

    }

    @Override
    public OjQuestion copyQuestion(OjQuestionVO ojQuestionVO) {
        OjQuestion ojQuestion = BeanCopyUtil.copyObject(ojQuestionVO, OjQuestion.class);
        List<JudgeCaseDTO> judgeCases = ojQuestionVO.getJudgeCase();
        if (Objects.nonNull(judgeCases)) {
            ojQuestion.setJudgeCase(toJSONString(judgeCases));
        }
        JudgeConfigDTO judgeConfig = ojQuestionVO.getJudgeConfig();
        if (Objects.nonNull(judgeConfig)) {
            ojQuestion.setJudgeConfig(toJSONString(judgeConfig));
        }
        return ojQuestion;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer addQuestion(OjQuestionVO ojQuestionVO) {
        OjQuestion ojQuestion = this.copyQuestion(ojQuestionVO);
        // 验证题目是否符合规范
        this.validateQuestion(ojQuestion, true);

        Integer userId = UserInfoContext.getUser().getUserInfoId();
        ojQuestion.setUserId(userId);
        this.save(ojQuestion);

        // 批量更新或新增Tag
        List<String> tags = ojQuestionVO.getTags();
        List<OjTag> ojTags = tags.stream().map(item -> {
            OjTag ojTag = new OjTag();
            ojTag.setTag(item);
            return ojTag;
        }).collect(Collectors.toList());

        LambdaQueryWrapper<OjTag> wrapper = new LambdaQueryWrapper<OjTag>()
                .in(OjTag::getTag, tags);
        List<OjTag> existTags = ojTagMapper.selectList(wrapper);

        Map<String, OjTag> existTagsMap = existTags.stream()
                .collect(Collectors.toMap(OjTag::getTag, entity -> entity));

        for (OjTag ojTag : ojTags) {
            OjTag existTag = existTagsMap.get(ojTag.getTag());
            Integer tagId;
            if (existTag != null) {
                Integer citationCount = existTag.getCitationCount();
                existTag.setCitationCount(++citationCount);
                ojTagMapper.updateById(existTag);
                tagId = existTag.getId();
            } else {
                ojTag.setCitationCount(1);
                ojTagMapper.insert(ojTag);
                tagId = ojTag.getId();
            }
            OjQuestionTag ojQuestionTag = OjQuestionTag.builder()
                    .questionId(ojQuestion.getId())
                    .tagId(tagId)
                    .build();
            ojQuestionTagService.save(ojQuestionTag);
        }

        Integer questionId = ojQuestion.getId();
        return questionId;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer updateQuestion(OjQuestionVO ojQuestionVO) {
        OjQuestion ojQuestion = this.copyQuestion(ojQuestionVO);
        // 验证题目是否符合规范
        this.validateQuestion(ojQuestion, false);

        // 移除题目tag
        Integer questionId = ojQuestionVO.getId();
        OjQuestionDTO ojQuestionDTO = ojQuestionMapper.selectQuestionById(questionId);
        List<OjTag> tags = ojQuestionDTO.getTags();

        ArrayList<OjTag> updateTagList = new ArrayList<>();
        ArrayList<Integer> deleteTagList = new ArrayList<>();

        tags.stream().forEach(tag -> {
            if (tag.getCitationCount() == 1) {
                deleteTagList.add(tag.getId());
            } else {
                Integer citationCount = tag.getCitationCount();
                tag.setCitationCount(--citationCount);
                updateTagList.add(tag);
            }
        });

        if (deleteTagList.size() > 0) {
            ojTagMapper.deleteBatchIds(deleteTagList);
        }
        for (OjTag tag : updateTagList) {
            ojTagMapper.updateById(tag);
        }

        // 根据题目id删除关联表
        ArrayList<Integer> ids = new ArrayList<>();
        ids.add(questionId);
        ojQuestionTagMapper.deleteByQuestionIds(ids);

        List<String> tagsStr = ojQuestionVO.getTags();
        List<OjTag> modifyTags = tagsStr.stream().map(item -> {
            OjTag ojTag = new OjTag();
            ojTag.setTag(item);
            return ojTag;
        }).collect(Collectors.toList());
        // 获取所有tag
        LambdaQueryWrapper<OjTag> wrapper = new LambdaQueryWrapper<OjTag>()
                .in(OjTag::getTag, tagsStr);
        List<OjTag> existTags = ojTagMapper.selectList(wrapper);

        Map<String, OjTag> existTagsMap = existTags.stream()
                .collect(Collectors.toMap(OjTag::getTag, entity -> entity));

        // 若tag不存在则新增，存在cite+1
        for (OjTag ojTag : modifyTags) {
            OjTag existTag = existTagsMap.get(ojTag.getTag());
            Integer tagId;
            if (existTag != null) {
                Integer citationCount = existTag.getCitationCount();
                existTag.setCitationCount(++citationCount);
                ojTagMapper.updateById(existTag);
                tagId = existTag.getId();
            } else {
                ojTag.setCitationCount(1);
                ojTagMapper.insert(ojTag);
                tagId = ojTag.getId();
            }
            OjQuestionTag ojQuestionTag = OjQuestionTag.builder()
                    .questionId(ojQuestion.getId())
                    .tagId(tagId)
                    .build();
            ojQuestionTagService.save(ojQuestionTag);
        }

        Integer userId = UserInfoContext.getUser().getUserInfoId();
        ojQuestion.setUserId(userId);
        ojQuestionMapper.updateById(ojQuestion);
        return questionId;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int removeQuestions(List<Integer> ids) {
        List<OjQuestionDTO> ojQuestionDTOS = ojQuestionMapper.selectQuestionByIds(ids);

        ArrayList<OjTag> updateTagList = new ArrayList<>();
        ArrayList<Integer> deleteTagList = new ArrayList<>();

        ojQuestionDTOS.stream().forEach((item) -> {
            List<OjTag> tags = item.getTags();
            tags.stream().forEach(tag -> {
                if (tag.getCitationCount() == 1) {
                    deleteTagList.add(tag.getId());
                } else {
                    Integer citationCount = tag.getCitationCount();
                    tag.setCitationCount(--citationCount);
                    updateTagList.add(tag);
                }
            });
        });

        if (deleteTagList.size() > 0) {
            ojTagMapper.deleteBatchIds(deleteTagList);
        }
        for (OjTag tag : updateTagList) {
            ojTagMapper.updateById(tag);
        }

        ojQuestionTagMapper.deleteByQuestionIds(ids);

        int i = ojQuestionMapper.deleteBatchIds(ids);
        return i;
    }

    @Override
    public OjQuestionDTO copyOjQuestionDTO(OjQuestionDTO ojQuestionDTO) {
        String judgeCaseString = ojQuestionDTO.getJudgeCaseStr();
        if (StringUtils.isNotBlank(judgeCaseString)) {
            List<JudgeCaseDTO> judgeCase = JSON.parseArray(judgeCaseString, JudgeCaseDTO.class);
            ojQuestionDTO.setJudgeCase(judgeCase);
        }
        String judgeConfigString = ojQuestionDTO.getJudgeConfigStr();
        if (StringUtils.isNotBlank(judgeConfigString)) {
            JudgeConfigDTO judgeConfig = JSON.parseObject(judgeConfigString, JudgeConfigDTO.class);
            ojQuestionDTO.setJudgeConfig(judgeConfig);
        }
        return ojQuestionDTO;
    }

    @SneakyThrows
    @Override
    public PageResultDTO<OjQuestionDTO> selectQuestionList(OjQuestionVO ojQuestionVO) {
        List<OjQuestionDTO> ojQuestions = ojQuestionMapper.selectQuestionList(ojQuestionVO, PageUtil.getLimitCurrent(), PageUtil.getSize());
        CompletableFuture<Long> supplyCount = CompletableFuture.supplyAsync(() -> ojQuestionMapper.selectQuestionCount(ojQuestionVO));
        List<OjQuestionDTO> ojQuestionDTOS = new ArrayList<>();
        ojQuestions.stream().forEach(item -> {
            OjQuestionDTO ojQuestionDTO = this.copyOjQuestionDTO(item);
            ojQuestionDTOS.add(ojQuestionDTO);
        });
        return new PageResultDTO<>(ojQuestionDTOS, supplyCount.get());
    }

    @Override
    public OjQuestionDTO selectQuestionById(Integer id) {
        OjQuestionDTO ojQuestion = ojQuestionMapper.selectQuestionById(id);
        return this.copyOjQuestionDTO(ojQuestion);
    }
}




