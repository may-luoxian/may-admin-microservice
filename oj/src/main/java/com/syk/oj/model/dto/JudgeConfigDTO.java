package com.syk.oj.model.dto;

import lombok.Data;

@Data
public class JudgeConfigDTO {
    private Long timeLimit;
    private Long stackLimit;
    private Long memoryLimit;
}
