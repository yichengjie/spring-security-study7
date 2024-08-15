package com.yicj.study.mapper;

import com.yicj.study.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * UserMapper
 * </p>
 *
 * @author yicj
 * @since 2024/8/15 22:34
 */
@Mapper
public interface UserMapper {

    @Select("select * from user")
    List<UserEntity> selectAll() ;
}
