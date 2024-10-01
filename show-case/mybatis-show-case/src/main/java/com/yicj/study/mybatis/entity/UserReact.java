package com.yicj.study.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yicj.study.mybatis.model.GradeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("user_react")
public class UserReact extends BaseEntity {

    @TableId(type = IdType.AUTO)
    private String id ;

    @TableField("name")
    private String name ;

    @TableField("account")
    private String account ;

    @TableField("password")
    private String password ;

    @TableField("role")
    private String role ;

    @TableField("grade")
    private GradeEnum grade ;

}
