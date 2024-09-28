package com.yicj.study.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author yicj
 * @since 2024/9/28 18:56
 */
@Slf4j
@RestController
@RequestMapping("/file")
public class FileController {

    @GetMapping("/download")
    public void download() {

    }

    @PostMapping(value = "/upload"/*, consumes = MediaType.MULTIPART_FORM_DATA_VALUE*/)
    public void upload(
            @RequestPart("file") MultipartFile file,
            @RequestParam("name") String name,
            HttpServletRequest request) {
        log.info("name:{}", name) ;
        log.info("original file name :{}", file.getOriginalFilename()) ;
        log.info("file name :{}", file.getName()) ;
        log.info("file size :{}", file.getSize()) ;
    }
}
