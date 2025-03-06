package com.syk.oj.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "oj_question_tag")
public class OjQuestionTag {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer questionId;

    private Integer tagId;
}
