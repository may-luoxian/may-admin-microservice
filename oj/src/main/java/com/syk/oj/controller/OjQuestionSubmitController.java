package com.syk.oj.controller;

import com.may.utils.enums.StatusCodeEnum;
import com.may.utils.exception.BizException;
import com.may.utils.getuserdetails.UserInfoContext;
import com.may.utils.model.dto.PageResultDTO;
import com.may.utils.model.vo.ResultVO;
import com.syk.oj.entity.OjQuestionSubmit;
import com.syk.oj.model.dto.DebugDetailDTO;
import com.syk.oj.model.dto.SubmissionRecordDTO;
import com.syk.oj.model.dto.WrongDetailDTO;
import com.syk.oj.model.vo.OjQuestionSubmitVO;
import com.syk.oj.model.vo.SubmissionRecordVO;
import com.syk.oj.service.OjQuestionSubmitService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Api(tags = "oj模块-题目提交")
@RestController
@RequestMapping("/questionSubmit")
@Slf4j
public class OjQuestionSubmitController {
    @Autowired
    private OjQuestionSubmitService ojQuestionSubmitService;

    @ApiOperation("提交题目")
    @PostMapping("/submit")
    public ResultVO<Integer> questionSubmit(@RequestBody OjQuestionSubmitVO questionSubmitVO) {
        if (questionSubmitVO == null || questionSubmitVO.getQuestionId() <= 0) {
            throw new BizException(StatusCodeEnum.VALID_ERROR.getDesc());
        }
        Integer userInfoId = UserInfoContext.getUser().getUserInfoId();
        questionSubmitVO.setUserInfoId(userInfoId);
        Integer questionSubmitId = ojQuestionSubmitService.questionSubmit(questionSubmitVO);
        return ResultVO.ok(questionSubmitId);
    }

    @ApiOperation("Debug题目")
    @PostMapping("/debug")
    public ResultVO<DebugDetailDTO> questionOperation(@RequestBody OjQuestionSubmitVO questionSubmitVO) {
        if (questionSubmitVO == null || questionSubmitVO.getQuestionId() <= 0) {
            throw new BizException(StatusCodeEnum.VALID_ERROR.getDesc());
        }
        DebugDetailDTO debugDetailDTO = ojQuestionSubmitService.questionDebug(questionSubmitVO);
        return ResultVO.ok(debugDetailDTO);
    }

    @ApiOperation("查询提交记录")
    @GetMapping("/")
    public ResultVO<PageResultDTO<SubmissionRecordDTO>> querySubmissionRecord(SubmissionRecordVO submissionRecordVO) {
        PageResultDTO<SubmissionRecordDTO> submissionRecordDTOPageResultDTO = ojQuestionSubmitService.querySubmissionRecord(submissionRecordVO);
        return ResultVO.ok(submissionRecordDTOPageResultDTO);
    }

    @ApiOperation("查询错误详情")
    @GetMapping("/wrongDetail/{id}")
    public ResultVO<WrongDetailDTO> queryWrongDetail(@PathVariable Integer id) {
        WrongDetailDTO wrongDetailDTO = ojQuestionSubmitService.queryWrongDetail(id);
        return ResultVO.ok(wrongDetailDTO);
    }
}
