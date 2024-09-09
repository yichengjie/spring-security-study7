package com.yicj.study.jpa.repository;

import com.yicj.study.jpa.entity.FeatureMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Meta;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * FeatureMessageRepository
 * </p>
 *
 * @author yicj
 * @since 2024/08/18 15:51
 */
@Repository
public interface FeatureMessageRepository
        extends JpaRepositoryImplementation<FeatureMessage, String> {

    @Query("select f from FeatureMessage f where f.status = ?1")
    List<FeatureMessage> listByStatus(Integer status, Sort sort) ;

    @Query("select f.id, f.messageHeadline, ifnull(f.seq, 999999999) as fnSeq from FeatureMessage f where f.status = ?1")
    List<Object[]> listByStatus2(Integer status, Sort sort);

    // messageHeadline like %?1%
    @Query("select f from FeatureMessage f where f.messageHeadline = ?1")
    Page<FeatureMessage> list4Page(String messageHeadline, Pageable pageable);

    @Meta(comment = "find roles by name")
    List<FeatureMessage> findByMessageHeadlineOrderByLastModifiedDateDesc(String messageHeadline);

    FeatureMessage findTopByMessageHeadlineOrderByLastModifiedDateDesc(String messageHeadline);

//    @Query(value = "select * from feature_message f where f.message_headline = ?1", nativeQuery = true)
//    List findByMessageHeadline(String messageHeadline);

}
