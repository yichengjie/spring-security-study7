package com.yicj.study.jpa.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.yicj.study.jpa.entity.UserInfo;
import com.yicj.study.jpa.mapper.UserInfoMapper;
import com.yicj.study.jpa.service.UserInfoService;
import com.yicj.study.jpa.utils.LambdaUpdateWrapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * UserInfoServiceImpl
 * </p>
 *
 * @author yicj
 * @since 2024/09/04 13:59
 */
@Service
@RequiredArgsConstructor
public class UserInfoServiceImpl implements UserInfoService {

    private final UserInfoMapper userInfoMapper ;

    @Override
    public List<UserInfo> listAll() {
        return userInfoMapper.selectList(null) ;
    }

    @Override
    public void deleteById(String id) {
        LambdaUpdateWrapper<UserInfo> wrapper = new LambdaUpdateWrapper<>() ;
        wrapper.eq(UserInfo::getId, id) ;
        LambdaUpdateWrapperUtil.supplyDeleteFlag(wrapper);
        userInfoMapper.update(wrapper) ;
    }

    @Override
    public String save(UserInfo userInfo) {
        return userInfoMapper.insert(userInfo) > 0 ? userInfo.getId() : null ;
    }
}
