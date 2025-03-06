package com.syk.oj.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.syk.oj.entity.OjTag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OjQuestionDTO {
    private Integer id;

    private String title;

    private Integer difficulty;

    private String content;

    private String mainCode;

    private String questionTemplate;

    private List<OjTag> tags;

    private String answer;

    private String answerCode;

    private Integer submitNum;

    private Integer acceptNum;

    private Float acceptRate;

    private String judgeConfigStr;

    private JudgeConfigDTO judgeConfig;

    private String judgeCaseStr;

    private List<JudgeCaseDTO> judgeCase;

    private Integer userId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    private Integer isDelete;
}
