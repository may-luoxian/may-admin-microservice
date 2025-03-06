package com.syk.oj.controller;

import com.may.utils.model.vo.ResultVO;
import com.syk.oj.model.dto.AcRateDTO;
import com.syk.oj.model.dto.CalendarDTO;
import com.syk.oj.model.dto.RankDTO;
import com.syk.oj.service.OjStatisticService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "oj模块-统计")
@RestController
@RequestMapping("/statistic")
public class OjStatisticController {

    @Autowired
    private OjStatisticService ojStatisticService;

    @GetMapping("/ac")
    public ResultVO<List<AcRateDTO>> statisticAcRate() {
        List<AcRateDTO> acRateDTOS = ojStatisticService.selectAcRate();
        return ResultVO.ok(acRateDTOS);
    }

    @GetMapping("/rank")
    public ResultVO<List<RankDTO>> statisticRank() {
        List<RankDTO> rankDTOS = ojStatisticService.selectRank();
        return ResultVO.ok(rankDTOS);
    }

    @GetMapping("/calendar/{date}")
    public ResultVO<List<CalendarDTO>> statisticCalendar(@PathVariable String date) {
        return ResultVO.ok(ojStatisticService.selectCalendar(date));
    }
}
