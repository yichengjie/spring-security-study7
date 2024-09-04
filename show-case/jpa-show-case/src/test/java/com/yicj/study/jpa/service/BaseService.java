package com.yicj.study.jpa.service;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.yicj.study.jpa.entity.BaseEntity;

/**
 * <p>
 * BaseService
 * </p>
 *
 * @author yicj
 * @since 2024/09/04 14:49
 */
public class BaseService<T extends BaseEntity> {

    void supplyDeleteFlag(LambdaUpdateWrapper<T> wrapper){
        wrapper.set(BaseEntity::getDeletedFlag, true) ;
    }
}
