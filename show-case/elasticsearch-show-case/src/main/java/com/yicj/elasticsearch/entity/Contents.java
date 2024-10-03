package com.yicj.elasticsearch.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("tb_contents")
public class Contents {

    @TableId
    @TableField("id")
    private Integer id;

    @TableField("title")
    private String title;

    @TableField("intro")
    private String intro;

    @TableField("content")
    private String content;

    @TableField("create_id")
    private Integer createId;

    @TableField("create_name")
    private String createName;

    @TableField("create_time")
    private LocalDateTime createTime;
}
