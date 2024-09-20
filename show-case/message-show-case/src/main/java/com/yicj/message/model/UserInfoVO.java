package com.yicj.message.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author yicj
 * @since 2024/9/20 12:53
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoVO implements Serializable {

    private String username ;

    private String address ;

    private String phone ;
}
