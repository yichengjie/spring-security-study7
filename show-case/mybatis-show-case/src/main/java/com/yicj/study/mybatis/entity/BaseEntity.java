package com.yicj.study.mybatis.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BaseEntity{

    @TableField("insert_time")
    private LocalDateTime insertTime ;

    @TableField("update_time")
    private LocalDateTime updateTime ;

}
