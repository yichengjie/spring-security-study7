package com.yicj.study.mybatis.entity

import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import lombok.Data
import lombok.ToString

@Data
@TableName("user_info")
class UserInfo(
    @TableId var id: String? = null,
    var username: String? = null,
    var password: String? = null,
    var address: String? = null) {
    override fun toString(): String {
        return "UserInfo(id=$id, username=$username, password=$password, address=$address)"
    }
}