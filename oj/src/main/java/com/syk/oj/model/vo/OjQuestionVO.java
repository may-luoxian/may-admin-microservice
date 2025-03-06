package com.syk.oj.model.vo;

import com.syk.oj.model.dto.JudgeCaseDTO;
import com.syk.oj.model.dto.JudgeConfigDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(description = "题目")
public class OjQuestionVO {
    @ApiModelProperty(name = "id", value = "题目id", dataType = "Integer")
    private Integer id;

    @ApiModelProperty(name = "title", value = "题目标题", dataType = "String")
    private String title;

    @ApiModelProperty(name = "difficulty", value = "难度", dataType = "Integer")
    private Integer difficulty;

    @ApiModelProperty(name = "mainCode", value = "主函数", dataType = "String")
    private String mainCode;

    @ApiModelProperty(name = "questionTemplate;", value = "题目模板", dataType = "String")
    private String questionTemplate;

    @ApiModelProperty(name = "content", value = "题目内容", dataType = "String")
    private String content;

    @ApiModelProperty(name = "tags", value = "标签", dataType = "List<String>")
    private List<String> tags;

    @ApiModelProperty(name = "answer", value = "题解", dataType = "String")
    private String answer;

    @ApiModelProperty(name = "answer", value = "正确代码", dataType = "String")
    private String answerCode;

    @ApiModelProperty(name = "judgeConfig", value = "判题配置", dataType = "String")
    private JudgeConfigDTO judgeConfig;

    @ApiModelProperty(name = "judgeCase", value = "判题用例", dataType = "String")
    private List<JudgeCaseDTO> judgeCase;

    @ApiModelProperty(name = "current", value = "页号", dataType = "Integer")
    private Integer current;

    @ApiModelProperty(name = "size", value = "分页大小", dataType = "Integer")
    private Integer size;

    @ApiModelProperty(name = "selTag", value = "题目标签（查询时使用）", dataType = "Integer")
    private Integer selTag;
}
