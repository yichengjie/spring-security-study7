package com.yicj.study.mybatis.service

import com.yicj.study.mybatis.CommonUtil
import com.yicj.study.mybatis.HelloMybatisApplication
import com.yicj.study.mybatis.entity.AccountInfo
import com.yicj.study.mybatis.entity.UserInfo
import lombok.extern.slf4j.Slf4j
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime

@SpringBootTest(classes = [HelloMybatisApplication::class])
class UserInfoServiceTest {

    @Autowired
    private val userInfoService: UserInfoService? = null

    private var log: Logger = LoggerFactory.getLogger(UserInfoServiceTest::class.java)

    @Test
    fun testSelect() {
        val userInfo = userInfoService!!.getById("1")
        println(userInfo)
    }

    @Test
    fun testSaveUserAndAccount() {
        val userId = CommonUtil.uuid()
        val user = this.initUser(userId)
        val account = this.initAccount(userId)
        userInfoService!!.saveUserAndAccount(user, account)
    }

    @Test
    fun testSaveUserAndAccount2() {
        val userId = CommonUtil.uuid()
        val user = this.initUser(userId)
        val account = this.initAccount(userId)
        userInfoService!!.saveUserAndAccount2(user, account)
    }


    @Test
    fun saveUserAndAccountMono(){
        val userId = CommonUtil.uuid()
        val user = this.initUser(userId)
        val account = this.initAccount(userId)
        userInfoService!!.saveUserAndAccountMono(user, account)
    }

    // init user
    private fun initUser(userId: String): UserInfo {
        val user = UserInfo()
        user.id = userId
        user.username = "test"
        user.password = "123"
        user.address = "beijing"
        return user
    }

    // init account
    private fun initAccount(userId: String): AccountInfo {
        val account = AccountInfo()
        account.userId = userId
        account.num = "1001"
        account.amount = 100
        account.lastModifyDate = LocalDateTime.now()
        return account
    }

}