package com.syk.oj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.may.utils.model.dto.PageResultDTO;
import com.syk.oj.entity.OjQuestionSubmit;
import com.syk.oj.model.dto.DebugDetailDTO;
import com.syk.oj.model.dto.SubmissionRecordDTO;
import com.syk.oj.model.dto.WrongDetailDTO;
import com.syk.oj.model.vo.OjQuestionSubmitVO;
import com.syk.oj.model.vo.SubmissionRecordVO;

/**
* @author sunyukun
* @description 针对表【oj_question_submit】的数据库操作Service
* @createDate 2024-01-28 06:19:53
*/
public interface OjQuestionSubmitService extends IService<OjQuestionSubmit> {
    Integer questionSubmit(OjQuestionSubmitVO questionSubmitVO);

    DebugDetailDTO questionDebug(OjQuestionSubmitVO questionSubmitVO);

    PageResultDTO<SubmissionRecordDTO> querySubmissionRecord(SubmissionRecordVO submissionRecordVO);

    WrongDetailDTO queryWrongDetail(Integer id);
}
