package com.yicj.study.jpa.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

/**
 * <p>
 * UesrInfo
 * </p>
 *
 * @author yicj
 * @since 2024/09/04 13:47
 */
@Data
@ToString(callSuper = true)
@TableName("user_info")
public class UserInfo extends BaseEntity {

    @TableField("username")
    private String username ;

    @TableField("password")
    private String password ;

    @TableField("email")
    private String email ;

    @TableField("phone")
    private String phone ;

    @TableField("address")
    private String address ;
}
