package com.yicj.elasticsearch.es.repository;

import com.yicj.elasticsearch.es.index.ContentsIndex;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContentsIndexRepository extends ElasticsearchRepository<ContentsIndex,Long> {

}