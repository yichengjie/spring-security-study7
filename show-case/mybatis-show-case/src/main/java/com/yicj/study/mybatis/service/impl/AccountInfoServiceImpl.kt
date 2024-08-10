package com.yicj.study.mybatis.service.impl

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.yicj.study.mybatis.entity.AccountInfo
import com.yicj.study.mybatis.mapper.AccountInfoMapper
import com.yicj.study.mybatis.service.AccountInfoService
import org.springframework.stereotype.Service

@Service
open class AccountInfoServiceImpl: ServiceImpl<AccountInfoMapper, AccountInfo>(), AccountInfoService