package com.yicj.study.kotlin

import org.springframework.stereotype.Service

@Service
class HelloKtService {
    fun hello(){
        println("hello")
    }

    fun sum(a: Int, b: Int): Int {
        return  a + b
    }

    fun sum2(a: Int, b: Int) = a + b
}