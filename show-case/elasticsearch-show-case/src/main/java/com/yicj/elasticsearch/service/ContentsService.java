package com.yicj.elasticsearch.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yicj.elasticsearch.entity.Contents;
import com.yicj.elasticsearch.mapper.ContentsMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContentsService {

    @Resource
    private ContentsMapper contentsMapper ;

    public List<Contents> queryAll (){
        return contentsMapper.selectList(new QueryWrapper<>());
    }

}
