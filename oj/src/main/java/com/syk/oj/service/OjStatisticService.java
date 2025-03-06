package com.syk.oj.service;


import com.syk.oj.model.dto.AcRateDTO;
import com.syk.oj.model.dto.CalendarDTO;
import com.syk.oj.model.dto.RankDTO;

import java.util.List;

public interface OjStatisticService {
    List<AcRateDTO> selectAcRate();

    List<RankDTO> selectRank();

    List<CalendarDTO> selectCalendar(String date);
}
