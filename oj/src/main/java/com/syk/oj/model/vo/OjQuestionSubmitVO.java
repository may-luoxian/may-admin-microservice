package com.syk.oj.model.vo;
import lombok.Data;
@Data
public class OjQuestionSubmitVO {
    private Integer questionId;

    private Integer userInfoId;

    private String language;

    private String code;

    private String input;
}
