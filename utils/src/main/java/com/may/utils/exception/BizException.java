package com.may.utils.exception;

import com.may.utils.enums.StatusCodeEnum;

public class BizException extends RuntimeException{
    private Integer code = StatusCodeEnum.FAIL.getCode();

    public final String message;

    public BizException(String message) {
        this.message = message;
    }

    public BizException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
