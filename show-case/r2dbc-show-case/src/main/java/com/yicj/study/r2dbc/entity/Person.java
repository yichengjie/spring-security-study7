package com.yicj.study.r2dbc.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yicj
 * @since 2024/9/11 21:47
 */
@Data
@Builder
@AllArgsConstructor
public class Person {

    private final String id;
    private final String name;
    private final int age;

}