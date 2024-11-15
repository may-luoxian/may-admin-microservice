package com.syk.oj.model.dto;

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

    private String content;

    private List<String> tags;

    private String answer;

    private Integer submitNum;

    private Integer acceptNum;

    private JudgeConfigDTO judgeConfig;

    private List<JudgeCaseDTO> judgeCase;

    private Integer userId;

    private Date createTime;

    private Date updateTime;

    private Integer isDelete;
}
