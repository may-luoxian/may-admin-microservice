package com.syk.oj.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum QuestionSubStateEnum {
    UNTREATED(0, "待判题"),

    IN_PROCESS(1, "判题中"),

    SUCCESS(2, "已完成"),

    FAIL(3, "判题出错");

    private Integer value;

    private String message;
}
