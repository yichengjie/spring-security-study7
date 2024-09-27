package com.yicj.study.r2dbc.repository;


import com.yicj.study.r2dbc.entity.Person;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author yicj
 * @since 2024/9/27
 */
@Repository
public interface PersonRepository extends ReactiveCrudRepository<Person, String> {

}
