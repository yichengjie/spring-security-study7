package com.yicj.elasticsearch.es.service;

import com.yicj.elasticsearch.entity.Contents;
import com.yicj.elasticsearch.es.index.ContentsIndex;
import com.yicj.elasticsearch.service.ContentsService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ContentsIndexService {

    @Resource
    private ContentsService contentsService ;

    @Resource
    private ElasticsearchTemplate template ;

    /**
     * 初始化索引结构和数据
     */
    public void initIndex (){
        // 处理索引结构
        IndexOperations indexOps = template.indexOps(ContentsIndex.class);
        if (indexOps.exists()){
            boolean delFlag = indexOps.delete();
            log.info("contents_index exists，delete:{}",delFlag);
            indexOps.createMapping(ContentsIndex.class);
        } else {
            log.info("contents_index not exists");
            indexOps.createMapping(ContentsIndex.class);
        }
        // 同步数据库表记录
        List<Contents> contentsList = contentsService.queryAll();
        if (!contentsList.isEmpty()){
            List<ContentsIndex> contentsIndexList = new ArrayList<>() ;
            contentsList.forEach(contents -> {
                ContentsIndex contentsIndex = new ContentsIndex() ;
                BeanUtils.copyProperties(contents,contentsIndex);
                contentsIndexList.add(contentsIndex);
            });
            template.save(contentsIndexList);
        }
        // ID查询
        ContentsIndex contentsIndex = template.get("10",ContentsIndex.class);
        log.info("contents-index-10:{}",contentsIndex);
    }
}
