package com.syk.oj.model.dto;

import com.baomidou.mybatisplus.annotation.*;
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
public class WrongDetailDTO {
        private Integer id;
        private Integer questionId;
        private Integer userInfoId;
        private String language;
        private String code;
        private JudgeInfo judgeInfo;
        private String detailMessage;
        private Integer status;
        private String answer;
        private LocalDateTime createTime;
        private LocalDateTime updateTime;
}
