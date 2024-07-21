package com.yicj.study.model;

import lombok.Data;

@Data
public class SysLog {

    /**
     * 日志名称
     */
    private String logName;

    /**
     * ip地址
     */
    private String ip;

    /**
     * 请求参数
     */
    private String requestParams;

    /**
     * 请求地址
     */
    private String requestUrl;

    /**
     * 用户ua信息
     */
    private String userAgent;

    /**
     * 请求时间
     */
    private Long useTime;

    /**
     * 请求时间
     */
    private String exceptionInfo;

    /**
     * 响应信息
     */
    private String responseInfo;

    /**
     * 用户名称
     */
    private String username;

    /**
     * 请求方式
     */
    private String requestMethod;

}

