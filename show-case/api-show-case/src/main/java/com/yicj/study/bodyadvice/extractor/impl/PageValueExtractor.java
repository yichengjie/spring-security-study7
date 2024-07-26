package com.yicj.study.bodyadvice.extractor.impl;

import com.yicj.study.bodyadvice.extractor.ValueExtractor;
import org.springframework.stereotype.Component;
import java.util.Map;

/**
 * <p>
 * PageValueExtractor
 * </p>
 *
 * @author yicj
 * @since 2024年07月26日 10:23
 */
@Component("pageValueExtractor")
public class PageValueExtractor implements ValueExtractor<Map<String,Object>> {

    @Override
    public Object extract(Map<String, Object> data) {
        return data.get("recordList");
    }

}
