package com.yicj.core.mvc;


import com.yicj.core.mvc.support.ExceptionAdvice;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Import;

@AutoConfiguration
@Import(ExceptionAdvice.class)
public class AscWebAutoConfiguration {

}
