package com.syk.oj.controller;

import com.may.utils.exception.BizException;
import com.may.utils.getuserdetails.UserInfoContext;
import com.may.utils.model.vo.ResultVO;
import com.syk.oj.entity.OjQuestion;
import com.syk.oj.model.dto.OjQuestionDTO;
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
    public ResultVO<Integer> addQuestion(@RequestBody OjQuestionDTO ojQuestionDTO) {
        OjQuestion ojQuestion = ojQuestionService.copyQuestion(ojQuestionDTO);
        // 验证题目是否符合规范
        ojQuestionService.validateQuestion(ojQuestion, true);

        Integer userId = UserInfoContext.getUser().getUserInfoId();
        ojQuestion.setUserId(userId);
        boolean save = ojQuestionService.save(ojQuestion);
        if (!save) {
            throw new BizException("题目添加失败");
        }
        Integer questionId = ojQuestion.getId();
        return ResultVO.ok(questionId);
    }

    @ApiOperation("修改题目")
    @PutMapping("/")
    public ResultVO<Integer> updateQuestion(@RequestBody OjQuestionDTO ojQuestionDTO) {
        OjQuestion ojQuestion = ojQuestionService.copyQuestion(ojQuestionDTO);
        // 验证题目是否符合规范
        ojQuestionService.validateQuestion(ojQuestion, false);

        Integer userId = UserInfoContext.getUser().getUserInfoId();
        ojQuestion.setUserId(userId);
        boolean update = ojQuestionService.updateById(ojQuestion);
        if (!update) {
            throw new BizException("题目修改失败");
        }
        Integer questionId = ojQuestion.getId();
        return ResultVO.ok(questionId);
    }

    @ApiOperation("删除题目")
    @DeleteMapping("/")
    public ResultVO<Boolean> deleteQuestion(@RequestBody List<Integer> ids) {
        boolean b = ojQuestionService.removeByIds(ids);
        return ResultVO.ok(b);
    }

    @ApiOperation("查询题目列表")
    @GetMapping("/")
    public ResultVO<List<OjQuestionDTO>> selectQuestions() {
        List<OjQuestionDTO> ojQuestionDTOS = ojQuestionService.selectQuestionList();
        return ResultVO.ok(ojQuestionDTOS);
    }

    @ApiOperation("根据id查询题目")
    @GetMapping("/selectById/{id}")
    public ResultVO<OjQuestionDTO> selectQuestionById(@PathVariable Integer id) {
        return ResultVO.ok(ojQuestionService.selectQuestionById(id));
    }
}
