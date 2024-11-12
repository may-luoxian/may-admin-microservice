package com.may.managementcenter.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_role_home")
public class RoleHome {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer homeId;

    private Integer roleId;

    private Integer orderNum;

    private Integer widthValue;
}
