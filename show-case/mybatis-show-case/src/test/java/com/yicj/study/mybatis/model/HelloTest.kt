package com.yicj.study.mybatis.model

import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory

class HelloTest {

    private val log = LoggerFactory.getLogger(HelloTest::class.java)

    fun parseInt(str: String): Int? {
        return str.toIntOrNull()
    }

    fun printProduct(arg1: String, arg2: String) {
        val x = parseInt(arg1)
        val y = parseInt(arg2)
        // 直接使用 `x * y` 会导致编译错误，因为它们可能为 null
        if (x != null && y != null) {
            println(x * y)
        }else{
            println("'$arg1' or '$arg2' is not a number")
        }
    }


    @Test
    fun foo(){
        listOf(1,2,3,4,5).forEach {
            if(it == 3) return // 非局部直接返回到 foo() 的调用者
            println(it)
        }
    }
}
