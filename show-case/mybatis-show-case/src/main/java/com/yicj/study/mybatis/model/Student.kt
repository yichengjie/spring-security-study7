package com.yicj.study.mybatis.model

class Student(name: String, age: Int, val num: String?): Person(name, age) {
    init {
        println("Student init")
    }

    constructor(name: String, age: Int) : this(name, age, null){
        println("Student secondary constructor")
    }


    override fun printInfo(){
        println("name : $name, age : $age, num : $num")
    }
}