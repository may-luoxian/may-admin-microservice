package com.syk.oj.controller;

import com.may.utils.exception.BizException;
import com.may.utils.getuserdetails.UserInfoContext;
import com.may.utils.model.dto.PageResultDTO;
import com.may.utils.model.vo.ResultVO;
import com.syk.oj.entity.OjQuestion;
import com.syk.oj.model.dto.OjQuestionDTO;
import com.syk.oj.model.vo.OjQuestionVO;
import com.syk.oj.service.OjQuestionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "oj模块-题目")
@RestController
@RequestMapping("/question")
@Slf4j
public class OjQuestionControl {
    @Autowired
    private OjQuestionService ojQuestionService;

    @ApiOperation("添加题目")
    @PostMapping("/")
    public ResultVO<Integer> addQuestion(@RequestBody OjQuestionVO ojQuestionVO) {
        Integer questionId = ojQuestionService.addQuestion(ojQuestionVO);
        return ResultVO.ok(questionId);
    }

    @ApiOperation("修改题目")
    @PutMapping("/")
    public ResultVO<Integer> updateQuestion(@RequestBody OjQuestionVO ojQuestionVO) {
        Integer questionId = ojQuestionService.updateQuestion(ojQuestionVO);
        return ResultVO.ok(questionId);
    }

    @ApiOperation("删除题目")
    @DeleteMapping("/")
    public ResultVO<Integer> deleteQuestion(@RequestBody List<Integer> ids) {
        int i = ojQuestionService.removeQuestions(ids);
        return ResultVO.ok(i);
    }

    @ApiOperation("查询题目列表")
    @GetMapping("/")
    public ResultVO<PageResultDTO<OjQuestionDTO>> selectQuestions(OjQuestionVO ojQuestionVO) {
        PageResultDTO<OjQuestionDTO> ojQuestionDTOS = ojQuestionService.selectQuestionList(ojQuestionVO);
        return ResultVO.ok(ojQuestionDTOS);
    }

    @ApiOperation("根据id查询题目")
    @GetMapping("/selectById/{id}")
    public ResultVO<OjQuestionDTO> selectQuestionById(@PathVariable Integer id) {
        return ResultVO.ok(ojQuestionService.selectQuestionById(id));
    }
}
