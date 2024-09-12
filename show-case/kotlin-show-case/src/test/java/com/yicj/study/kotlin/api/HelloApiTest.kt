package com.yicj.study.kotlin.api


fun main(){
    //stringTemplate()
    //println("max of 0 and 42 is ${maxOf(0, 42)}")
    //forLoop()
    //testWhen()
    //collectionIn()
    filterOperation()
}


fun stringTemplate(){
    var a = 1
    val s1 = "a is $a"
    a = 2
    val s2 = "${s1.replace("is", "was")}, but now is $a"
    println(s2)
}

fun maxOf(a: Int, b: Int) : Int{
    if (a > b){
        return a ;
    }else{
        return b ;
    }
}

fun forLoop(){
    val items = listOf("apple", "banana", "kiwi")
    for (index in items.indices){
        println("item at $index is ${items[index]}")
    }
}


fun testWhen(){
    println(describe(1))
    println(describe("Hello"))
    println(describe(1000L))
    println(describe(2))
    println(describe("other"))
}

fun describe(obj: Any): String =
    when(obj){
        1 -> "One"
        "Hello" -> "Greeting"
        is Long -> "Long"
        !is String -> "Not a string"
        else -> "Unknown"
    }


fun collectionIn(){
    val items = setOf("apple", "banana", "kiwi")
    when{
        "orange" in items -> println("juicy")
        "apple" in items -> println("apple is fine too")
        "banana" in items -> println("banana is fine too")
    }
}


fun filterOperation(){
    val fruits = listOf("banana", "avocado", "apple", "kiwi")
    fruits.filter { it.startsWith("a") }
        .sortedBy { it }
        .map { it.toUpperCase() }
        .forEach { println(it) }
}


fun dataType(){
    val x = 5 / 2.toDouble()
    println(x == 2.5)
}