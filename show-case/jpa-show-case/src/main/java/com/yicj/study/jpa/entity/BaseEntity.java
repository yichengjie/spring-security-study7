package com.yicj.study.jpa.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * BaseEntity
 * </p>
 *
 * @author yicj
 * @since 2024/09/04 13:45
 */
@Data
public class BaseEntity implements Serializable {

    @TableField("id")
    @TableId(type = IdType.ASSIGN_UUID)
    private String id ;

    @TableField("created_by")
    private String createdBy ;

    @TableField("last_modified_by")
    private String lastModifiedBy ;

    @TableField("created_date")
    private LocalDateTime createdDate ;

    @TableField("last_modified_date")
    private LocalDateTime lastModifiedDate ;

    @TableField("deleted_flag")
    private Boolean deletedFlag ;
}
