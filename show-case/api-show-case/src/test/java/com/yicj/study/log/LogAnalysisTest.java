package com.yicj.study.log;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * LogAnalysisTest
 * </p>
 *
 * @author yicj
 * @since 2024年07月31日 11:33
 */
@Slf4j
public class LogAnalysisTest {

    @Test
    void test() throws IOException {
        String fullFilePath = "D:\\1111\\quote\\multi-thread.txt" ;
        Files.lines(Paths.get(fullFilePath)).forEach(line -> {
            if (line.contains("====> taskInfo :")) {
                System.out.println(line);
            }
        });
    }

    @Test
    void order() throws IOException {
        List<LineInfo> retList = new ArrayList<>() ;
        String fullFilePath = "D:\\1111\\quote\\multi-thread.txt" ;
        Files.lines(Path.of(fullFilePath)).forEach(line -> {
            retList.add(LineInfo.parse(line)) ;
        });
        retList.stream().sorted().forEach(item -> System.out.println(item.getValue()));
    }


    @Data
    @AllArgsConstructor
    static class LineInfo implements Comparable<LineInfo>{
        private LocalDate startDate ;

        private String value ;

        public static LineInfo parse(String value){
            // ====> taskInfo : (2021-09-29, 2021-10-09) task , take time: 1.011
            String content = StringUtils.substringAfter(value, "====> taskInfo : (");
            content = StringUtils.substringBefore(content, ") task");
            String [] arr = content.split(",") ;
            LocalDate startDate = LocalDate.parse(arr[0]);
            return new LineInfo(startDate, value) ;
        }

        @Override
        public int compareTo(LineInfo o) {
            return this.startDate.compareTo(o.startDate);
        }
    }


}
