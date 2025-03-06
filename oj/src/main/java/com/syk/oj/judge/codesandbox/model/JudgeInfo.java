package com.syk.oj.judge.codesandbox.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JudgeInfo {
    private List<String> inputList;

    private List<String> outputList;

    private List<String> expectedResult;

    private String message;

    private Integer result;

    private Long time;

    private Long memory;
}
