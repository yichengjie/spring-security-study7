package com.yicj.study.jpa.utils;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.yicj.study.jpa.entity.BaseEntity;

/**
 * <p>
 * LambdaUpdateWrapperTest
 * </p>
 *
 * @author yicj
 * @since 2024/09/04 14:51
 */
public class LambdaUpdateWrapperUtil {

    private LambdaUpdateWrapperUtil(){}

    public static <T extends BaseEntity> void supplyDeleteFlag(LambdaUpdateWrapper<T> wrapper){
        //new SFunction<T,Boolean>()
        wrapper.set(T::getDeletedFlag, true) ;
    }
}
