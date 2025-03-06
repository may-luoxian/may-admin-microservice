package com.syk.oj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.syk.oj.entity.OjQuestion;
import com.syk.oj.model.dto.OjQuestionDTO;
import com.syk.oj.model.vo.OjQuestionVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author sunyukun
 * @description 针对表【oj_question(oj模块-题目)】的数据库操作Mapper
 * @createDate 2024-01-28 06:17:29
 * @Entity com.myblog.entity.OjQuestion
 */
@Repository
public interface OjQuestionMapper extends BaseMapper<OjQuestion> {
    List<OjQuestionDTO> selectQuestionList(@Param("ojQuestionVO") OjQuestionVO ojQuestionVO, @Param("current") Long current, @Param("size") Long size);

    Long selectQuestionCount(@Param("ojQuestionVO") OjQuestionVO ojQuestionVO);

    OjQuestionDTO selectQuestionById(@Param("id") Integer id);

    List<OjQuestionDTO> selectQuestionByIds(@Param("ids") List<Integer> ids);
}




