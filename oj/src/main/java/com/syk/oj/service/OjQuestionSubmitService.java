package com.syk.oj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.syk.oj.entity.OjQuestionSubmit;
import com.syk.oj.model.vo.OjQuestionSubmitVO;

/**
* @author sunyukun
* @description 针对表【oj_question_submit】的数据库操作Service
* @createDate 2024-01-28 06:19:53
*/
public interface OjQuestionSubmitService extends IService<OjQuestionSubmit> {
    Integer questionSubmit(OjQuestionSubmitVO questionSubmitVO);
}
