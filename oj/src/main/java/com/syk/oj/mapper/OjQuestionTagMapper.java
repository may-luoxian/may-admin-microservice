package com.syk.oj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.syk.oj.entity.OjQuestionTag;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OjQuestionTagMapper extends BaseMapper<OjQuestionTag> {
    void deleteByQuestionIds(@Param("ids") List<Integer> ids);
}
