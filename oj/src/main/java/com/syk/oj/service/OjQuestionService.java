package com.syk.oj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.syk.oj.entity.OjQuestion;
import com.syk.oj.model.dto.OjQuestionDTO;

import java.util.List;

/**
* @author sunyukun
* @description 针对表【oj_question(oj模块-题目)】的数据库操作Service
* @createDate 2024-01-28 06:17:29
*/
public interface OjQuestionService extends IService<OjQuestion> {
    void validateQuestion(OjQuestion ojQuestion, boolean add);

    OjQuestion copyQuestion(OjQuestionDTO ojQuestionDTO);

    OjQuestionDTO copyOjQuestionDTO(OjQuestion ojQuestion);

    List<OjQuestionDTO> selectQuestionList();

    OjQuestionDTO selectQuestionById(Integer id);
}
