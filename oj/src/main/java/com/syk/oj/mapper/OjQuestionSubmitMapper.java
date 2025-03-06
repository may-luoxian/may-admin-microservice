package com.syk.oj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.syk.oj.entity.OjQuestionSubmit;
import com.syk.oj.model.dto.SubmissionRecordDTO;
import com.syk.oj.model.vo.SubmissionRecordVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author sunyukun
 * @description 针对表【oj_question_submit】的数据库操作Mapper
 * @createDate 2024-01-28 06:19:53
 * @Entity com.myblog.entity.OjQuestionSubmit
 */
@Repository
public interface OjQuestionSubmitMapper extends BaseMapper<OjQuestionSubmit> {
    List<SubmissionRecordDTO> querySubmissionRecord(@Param("current") Long current, @Param("size") Long size, @Param("submissionRecordVO") SubmissionRecordVO submissionRecordVO);

    Long querySubmissionRecordCount(@Param("submissionRecordVO") SubmissionRecordVO submissionRecordVO);
}




