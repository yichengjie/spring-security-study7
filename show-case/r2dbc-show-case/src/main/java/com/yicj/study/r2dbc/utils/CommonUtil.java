package com.yicj.study.r2dbc.utils;


import java.util.UUID;

/**
 * @author yicj
 * @since 2024/9/27
 */
public class CommonUtil {

    // uuid
    public static String uuid(){
        return UUID.randomUUID().toString().replace("-", "") ;
    }
}
