package com.may.utils.model.vo;

import com.may.utils.enums.StatusCodeEnum;

import static com.may.utils.enums.StatusCodeEnum.FAIL;
import static com.may.utils.enums.StatusCodeEnum.SUCCESS;

public class ResultVO<T> {
    private Boolean flag;
    private Integer code;
    private String message;
    private T data;

    public static <T> ResultVO<T> ok() {
        return resultVO(true, SUCCESS.getCode(), SUCCESS.getDesc(), null);
    }

    public static <T> ResultVO<T> ok(T data) {
        return resultVO(true, SUCCESS.getCode(), SUCCESS.getDesc(), data);
    }

    public static <T> ResultVO<T> fail() {
        return resultVO(false, FAIL.getCode(), FAIL.getDesc(), null);
    }

    public static <T> ResultVO<T> fail(StatusCodeEnum statusCodeEnum) {
        return resultVO(false, statusCodeEnum.getCode(), statusCodeEnum.getDesc(), null);
    }

    public static <T> ResultVO<T> fail(String message) {
        return resultVO(false, message);
    }

    public static <T> ResultVO<T> fail(Integer code, String message) {
        return resultVO(false, code, message, null);
    }

    private static <T> ResultVO<T> resultVO(Boolean flag, String message) {
        ResultVO<T> resultVO = new ResultVO<>();
        resultVO.setFlag(flag);
        resultVO.setCode(flag ? SUCCESS.getCode() : FAIL.getCode());
        resultVO.setMessage(message);
        return resultVO;
    }

    private static <T> ResultVO<T> resultVO(Boolean flag, Integer code, String message, T data) {
        ResultVO<T> resultVO = new ResultVO<>();
        resultVO.setFlag(flag);
        resultVO.setCode(code);
        resultVO.setMessage(message);
        resultVO.setData(data);
        return resultVO;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
