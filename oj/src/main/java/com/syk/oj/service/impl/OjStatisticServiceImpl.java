package com.syk.oj.service.impl;

import com.may.utils.getuserdetails.UserInfoContext;
import com.syk.oj.mapper.OjStatisticMapper;
import com.syk.oj.model.dto.AcRateDTO;
import com.syk.oj.model.dto.CalendarDTO;
import com.syk.oj.model.dto.RankDTO;
import com.syk.oj.service.OjStatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OjStatisticServiceImpl implements OjStatisticService {

    @Autowired
    private OjStatisticMapper ojStatisticMapper;
    @Override
    public List<AcRateDTO> selectAcRate() {
        return ojStatisticMapper.selectAcRate();
    }
    @Override
    public List<RankDTO> selectRank() {
        return ojStatisticMapper.selectRank();
    }

    @Override
    public List<CalendarDTO> selectCalendar(String date) {
        String startDate = date + "-01-01";
        String endDate = date + "-12-31";
        Integer userInfoId = UserInfoContext.getUser().getUserInfoId();
        List<CalendarDTO> calendarDTOS = ojStatisticMapper.selectCalendar(startDate, endDate, userInfoId);
        return calendarDTOS;
    }
}
