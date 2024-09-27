package com.yicj.study.jpa.repository;


import com.yicj.study.jpa.entity.UserReact;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author yicj
 * @since 2024/9/27
 */
@Repository
public interface UserRepository  extends CrudRepository<UserReact, String> {


}
