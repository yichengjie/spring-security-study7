package com.yicj.study.jpa.repository;

import com.yicj.study.jpa.entity.FeatureMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

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
}
