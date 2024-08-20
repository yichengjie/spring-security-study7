package com.yicj.study.jpa.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * PageInfo
 * </p>
 *
 * @author yicj
 * @since 2024/08/20 18:01
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageInfo {
    private int pageIndex;

    private int pageSize;

    private int pageCount;

    private long totalElementCount;

    public static PageInfo getDefaultEmptyPageInfo() {
        return PageInfo.builder()
                .pageCount(0)
                .pageIndex(0)
                .pageSize(10)
                .totalElementCount(0)
                .build();
    }
}