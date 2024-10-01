package com.yicj.study.mybatis.model;


import lombok.Data;

import java.util.List;

@Data
public class User {

    private Boolean admin ;

    private String userName ;

    private List<String> shops ;

}
