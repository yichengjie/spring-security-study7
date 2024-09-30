package com.yicj.core.swagger;


import com.yicj.core.swagger.config.AscSwaggerConfig;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Import;

@AutoConfiguration
@Import(AscSwaggerConfig.class)
public class AscSwaggerConfiguration {


}
