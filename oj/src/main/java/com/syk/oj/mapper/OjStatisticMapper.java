package com.syk.oj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.syk.oj.entity.OjTag;
import com.syk.oj.model.dto.AcRateDTO;
import com.syk.oj.model.dto.CalendarDTO;
import com.syk.oj.model.dto.RankDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OjStatisticMapper {
    List<AcRateDTO> selectAcRate();

    List<RankDTO> selectRank();

    List<CalendarDTO> selectCalendar(@Param("startDate") String startDate, @Param("endDate") String endDate, @Param("id") Integer id);
}
