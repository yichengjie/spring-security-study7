package com.yicj.study.javers

class HelloKtService {
    fun hello(){
        println("hello")
    }

    fun sum(a: Int, b: Int): Int {
        return  a + b
    }

    fun sum2(a: Int, b: Int) = a + b
}