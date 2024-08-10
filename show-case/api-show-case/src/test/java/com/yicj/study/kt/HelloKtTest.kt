package com.yicj.study.kt

import com.yicj.study.javers.HelloKtService
import lombok.extern.slf4j.Slf4j
import org.junit.Test

@Slf4j
class HelloKtTest {

    interface Hello{
        fun hello()
    }

    class HelloImpl : Hello{
        override fun hello() {
            println("hello")
        }
    }

    @Test
    fun hello(){

    }
}

fun main(args: Array<String>) {
//    val items2 = listOf("apple", "banana", "kiwi")
//    for (index in items2.indices){
//        println("item at $index is ${items2[index]}")
//    }
//    var index = 0
//    while (index < items2.size){
//        println("item at $index is ${items2[index]}")
//        index ++
//    }
    HelloKtService().hello()
}

fun sum(a: Int, b: Int): Int {
    return  a + b
}

fun sum2(a: Int, b: Int) = a + b