package com.yicj.study.controller

import com.yicj.study.kotlin.HelloKtService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/kotlin")
class HelloKotlinController(
    @Autowired var helloKtService: HelloKtService) {

    @GetMapping("/index")
    fun blog(model: Model): String {
        model.addAttribute("title", "Blog")
        helloKtService.hello();
        return "blog"
    }
}