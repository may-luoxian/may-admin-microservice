package com.syk.oj.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum JudgeInfoMsgEnum {
    ACCEPTED("Accepted", "答案正确", 1),

    WRONG_ANSWER("WrongAnswer", "答案错误", 2),

    COMPILE_ERROR("CompileError", "编译错误", 3),

    MEMORY_LIMIT_EXCEEDED("MemoryLimitExceeded", "内存溢出", 4),

    TIME_LIMIT_EXCEEDED("TimeLimitExceeded", "超时", 5),

    PRESENTATION_ERROR("PresentationError", "展示错误", 6),

    OUTPUT_LIMIT_EXCEEDED("OutputLimitExceeded", "输出溢出", 7),

    WAITING("Waiting", "等待中", 8),

    DANGEROUS_OPERATION("DangerousOperation", "危险操作", 9),

    RUNTIME_ERROR("RuntimeError", "运行时错误", 10),

    SYSTEM_ERROR("SystemError", "系统错误", 11);

    private String value;
    private String message;
    private Integer key;
}
