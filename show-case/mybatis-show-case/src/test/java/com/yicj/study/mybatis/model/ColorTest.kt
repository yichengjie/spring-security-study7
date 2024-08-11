package com.yicj.study.mybatis.model

import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory

class ColorTest {

    private val log = LoggerFactory.getLogger(ColorTest::class.java)

    @Test
    fun hello(){
        val color = Color.RED
        log.info("color : {}", color)

        var fromCode = Color.fromCode("2")
        log.info("fromCode : {}", fromCode)
    }
}