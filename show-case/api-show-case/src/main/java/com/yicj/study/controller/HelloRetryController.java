package com.yicj.study.controller;

import com.yicj.study.retry.HelloRetryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * HelloRetryController
 * </p>
 *
 * @author yicj
 * @since 2024/09/03 16:33
 */
@RestController
@RequestMapping("/retry")
@RequiredArgsConstructor
public class HelloRetryController {
    private final HelloRetryService helloRetryService ;

    @GetMapping("/index/{code}")
    public int index(@PathVariable("code") int code) throws Exception {
        return helloRetryService.hello(code);
    }

    @GetMapping("/retry/{code}")
    public int retry(@PathVariable("code") int code) throws Exception {
        return helloRetryService.retry(code);
    }
}
