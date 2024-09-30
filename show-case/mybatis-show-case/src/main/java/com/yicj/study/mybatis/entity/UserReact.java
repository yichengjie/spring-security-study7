package com.yicj.study.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("user_react")
public class UserReact {

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

    @TableField("insert_time")
    private LocalDateTime insertTime ;

    @TableField("update_time")
    private LocalDateTime updateTime ;

}
