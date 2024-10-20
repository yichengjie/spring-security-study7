package com.yicj.study.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("person")
public class Person extends Model<Person> {

    @TableField("id")
    private String id ;

    @TableField("name")
    private String name ;

    @TableField("age")
    private Integer age ;
}
