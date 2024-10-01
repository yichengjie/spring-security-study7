package com.yicj.study.mybatis.model;


import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public enum GradeEnum implements IEnum<Integer> {
    PRIMARY(1, "小学"),
    SECONDARY(2, "中学"),
    HIGH(3, "高中");

    //@EnumValue // 标记数据库存的值是code
    private final int code;

    private final String name;

    @Override
    public Integer getValue() {
        return this.getCode();
    }
}
