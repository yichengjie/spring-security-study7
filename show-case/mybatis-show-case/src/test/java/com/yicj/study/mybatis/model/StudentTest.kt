package com.yicj.study.mybatis.model

import org.junit.jupiter.api.Test

class StudentTest {

    @Test
    fun hello(){
        val person: Person = Student("123", 12, "123")
        person.printInfo()
    }

    @Test
    fun hello2(){
        val student: Student = Student("123", 12, "123")
        println(student)
    }
}