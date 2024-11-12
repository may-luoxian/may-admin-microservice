package com.syk.oj.controller;

import com.may.utils.enums.StatusCodeEnum;
import com.may.utils.exception.BizException;
import com.may.utils.getuserdetails.UserInfoContext;
import com.may.utils.model.vo.ResultVO;
import com.syk.oj.model.vo.OjQuestionSubmitVO;
import com.syk.oj.service.OjQuestionSubmitService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @ApiOperation("运行题目")
    @PostMapping("/operation")
    public ResultVO<Integer> questionOperation(OjQuestionSubmitVO questionSubmitVO) {
        return ResultVO.ok();
    }
}
