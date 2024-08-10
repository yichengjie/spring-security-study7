package com.yicj.study.mybatis.mapper

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.yicj.study.mybatis.entity.AccountInfo
import org.apache.ibatis.annotations.Mapper

@Mapper
interface AccountInfoMapper : BaseMapper<AccountInfo>