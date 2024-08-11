package com.yicj.study.mybatis.entity

import lombok.Data
import java.time.LocalDateTime

class AccountInfo {

    var id: String? = null
    var userId : String? = null
    var num: String? = null
    var amount: Int? = null
    var lastModifyDate: LocalDateTime? = null

    override fun toString(): String {
        return "AccountInfo{" +
                "id= $id" +
                ", userId='$userId'" +
                ", num='$num'" +
                ", amount=$amount" +
                ", lastModifyDate='$lastModifyDate'}"
    }
}