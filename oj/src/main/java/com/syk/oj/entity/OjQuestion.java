package com.syk.oj.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * oj模块-题目
 *
 * @TableName oj_question
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "oj_question")
public class OjQuestion implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 题目标题
     */
    private String title;

    /**
     * 难度
     */
    private Integer difficulty;

    /**
     * 题目内容
     */
    private String content;

    /**
     * 主函数代码
     */
    private String mainCode;

    /**
     * 题目模板
     */
    private String questionTemplate;

    /**
     * 题解
     */
    private String answer;

    /**
     * 正确代码
     */
    private String answerCode;

    /**
     * 题目提交数
     */
    private Integer submitNum;

    /**
     * 题目通过数
     */
    private Integer acceptNum;

    /**
     * 判题配置（json数组）
     * <p>
     * json存储类型
     * [
     * {
     * input: 'xxx',  输入用例
     * output: 'xxx'  输出用例
     * },
     * ]
     */
    private String judgeConfig;

    /**
     * 判题用例（json数组）
     * <p>
     * [
     * {
     * timeLimit: 'xxx',  时间限制
     * memoryLimit: 'xxx'  内存限制
     * }
     * ]
     */
    private String judgeCase;

    /**
     * 创建题目用户id
     */
    private Integer userId;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

    /**
     * 是否删除
     */
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
