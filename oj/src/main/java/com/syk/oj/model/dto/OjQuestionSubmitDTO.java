package com.syk.oj.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OjQuestionSubmitDTO {

    private Integer questionId;

    private Integer userInfoId;

    private String language;

    private String code;

    private String judgeInfo;

    private Integer status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Integer isDelete;
}
