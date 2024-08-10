package com.yicj.study.mybatis.service

import com.baomidou.mybatisplus.extension.service.IService
import com.yicj.study.mybatis.entity.AccountInfo
import com.yicj.study.mybatis.entity.UserInfo
import reactor.core.publisher.Mono

interface UserInfoService : IService<UserInfo>{
    fun saveUserAndAccount(user: UserInfo, account: AccountInfo) : String?

    fun saveUserAndAccount2(user: UserInfo, account: AccountInfo) : String?

    fun saveUserAndAccountMono(user: UserInfo, account: AccountInfo) : String?

}