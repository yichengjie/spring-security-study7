package com.yicj.study.mybatis.model

open class Person(var name: String, var age: Int) {
    open fun printInfo(){
        println("name : $name, age : $age")
    }
}