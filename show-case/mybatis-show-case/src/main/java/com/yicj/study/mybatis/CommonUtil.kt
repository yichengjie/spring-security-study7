package com.yicj.study.mybatis

import java.util.UUID

class CommonUtil private constructor(){
    companion object {
        fun uuid(): String {
            return UUID.randomUUID().toString().replace("-", "")
        }
    }
}