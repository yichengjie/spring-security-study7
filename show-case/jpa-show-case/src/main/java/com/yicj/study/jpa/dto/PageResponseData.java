package com.yicj.study.jpa.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * <p>
 * PageResponseData
 * </p>
 *
 * @author yicj
 * @since 2024/08/20 18:01
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PageResponseData<T> {
    PageInfo pageInfo;
    List<T> content;
}
