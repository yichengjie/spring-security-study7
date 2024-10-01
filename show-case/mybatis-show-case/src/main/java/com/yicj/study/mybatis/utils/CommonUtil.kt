package com.yicj.study.mybatis.utils

import java.util.UUID

class CommonUtil private constructor(){
    companion object {
        @JvmStatic
        fun uuid(): String {
            return UUID.randomUUID().toString().replace("-", "")
        }
    }
}