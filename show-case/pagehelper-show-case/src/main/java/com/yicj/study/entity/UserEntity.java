package com.yicj.study.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * UserEntity
 * </p>
 *
 * @author yicj
 * @since 2024/8/15 22:34
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    private String id;

    private String username ;

    private String password ;

    private String address ;

}
