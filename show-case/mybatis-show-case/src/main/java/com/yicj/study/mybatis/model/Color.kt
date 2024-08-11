package com.yicj.study.mybatis.model

enum class Color(var code: String, var desc: String) {
    RED("1", "red"),
    GREEN("2", "green"),
    BLUE("3", "blue") ;
    companion object {
        fun fromCode(code: String): Color {
            for (color in values()) {
                if (color.code == code) {
                    return color
                }
            }
            throw IllegalArgumentException("invalid code: $code")
        }
    }
}