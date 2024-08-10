package com.yicj.study.mybatis.service.impl

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.yicj.study.mybatis.CommonUtil
import com.yicj.study.mybatis.entity.AccountInfo
import com.yicj.study.mybatis.entity.UserInfo
import com.yicj.study.mybatis.mapper.AccountInfoMapper
import com.yicj.study.mybatis.mapper.UserInfoMapper
import com.yicj.study.mybatis.service.UserInfoService
import org.apache.commons.lang3.StringUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono
import java.util.concurrent.CompletableFuture

@Service
open class UserInfoServiceImpl(private var accountMapper: AccountInfoMapper):
        ServiceImpl<UserInfoMapper, UserInfo>(), UserInfoService {

    private val logger: Logger = LoggerFactory.getLogger(UserInfoServiceImpl::class.java)
    @Transactional(rollbackFor = [Exception::class])
    override fun saveUserAndAccount(user: UserInfo, account: AccountInfo): String? {
        // init data id
        this.initData(user, account)
        // save user data
        this.save(user)
        // save account data
        accountMapper.insert(account)
        //  return user id
        return user.id
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun saveUserAndAccount2(user: UserInfo, account: AccountInfo): String? {
        initData(user, account)
        val f1 = CompletableFuture.supplyAsync {
            this.save(user)
            logger.info("user id : {}", user.id)
            var a = 1/0
            user.id
        }

        val f2 = CompletableFuture.runAsync {
            logger.info("account id : {}", account.id)
            accountMapper.insert(account)
        }
        CompletableFuture.allOf(f1, f2).join()
        return f1.get()
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun saveUserAndAccountMono(user: UserInfo, account: AccountInfo): String? {
        // 这里有报错，但是事务不会回滚？
        this.initData(user, account)
        var userMono = Mono.fromCallable {
            logger.info("user id : {}", user.id)
            // 模拟一个耗时的计算或操作
            this.save(user)
            var a = 1/0
            user.id
        }

        val accountMono = Mono.fromCallable{
            logger.info("account id : {}", account.id)
            accountMapper.insert(account)
            account.id
        }
        Mono.zip(userMono, accountMono).subscribe()
        return user.id
    }

    private fun initData(user: UserInfo, account: AccountInfo){
        if (StringUtils.isBlank(user.id)) {
            user.id = CommonUtil.uuid()
        }
        if (StringUtils.isBlank(account.id)) {
            account.id = CommonUtil.uuid()
        }
    }
}