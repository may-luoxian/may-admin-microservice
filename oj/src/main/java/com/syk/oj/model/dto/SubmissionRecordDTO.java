package com.syk.oj.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.syk.oj.judge.codesandbox.model.JudgeInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubmissionRecordDTO {

    private Integer id;

    private String title;

    private Integer questionId;

    private Integer userInfoId;

    private String nickname;

    private String language;

    private String judgeInfoJson;

    private JudgeInfo judgeInfo;

    private Integer status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
}
