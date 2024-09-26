package com.yicj.study;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Set;

@Slf4j
@SpringBootApplication
public class ClientApplication {
    public static void main(String[] args) {
        //SpringApplication.run(ClientApplication.class, args) ;
        System.exit(SpringApplication.exit(SpringApplication.run(ClientApplication.class, args)));
    }

    @Bean
    public ExitCodeGenerator exitCodeGenerator() {
        return () -> 42;
    }

    @Component
    static class MyBean{

        public MyBean(ApplicationArguments args) {
            boolean debug = args.containsOption("debug");
            List<String> nonOptionArgs = args.getNonOptionArgs();

            for (String each : nonOptionArgs) {
                log.info("nonOptionArgs : {}", each);
            }

            // 所有参数
            String[] sourceArgs = args.getSourceArgs();
            for (String each : sourceArgs) {
                log.info("sourceArgs : {}", each);
            }
            // 有key参数的key
            Set<String> optionNames = args.getOptionNames();
            for (String optionName : optionNames) {

                // 有key参数的value
                List<String> optionValues = args.getOptionValues(optionName);

                for (String optionValue : optionValues) {
                    log.info("optionName : {} , optionValue : {}", optionName, optionValue);
                }
            }
            // if run with "--debug logfile.txt" prints ["logfile.txt"]
        }
    }

}