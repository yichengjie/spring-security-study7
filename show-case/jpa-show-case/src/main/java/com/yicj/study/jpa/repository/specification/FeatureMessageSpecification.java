package com.yicj.study.jpa.repository.specification;

import com.yicj.study.jpa.entity.FeatureMessage;
import com.yicj.study.jpa.entity.FeatureMessage_;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;
import java.time.LocalDateTime;

/**
 * <p>
 * FeatureMessageSpecification
 * </p>
 *
 * @author yicj
 * @since 2024/8/20 22:22
 */
@UtilityClass
public class FeatureMessageSpecification {

    public static Specification<FeatureMessage> eqActiveFlag(Integer effectiveFlag) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(FeatureMessage_.ACTIVE_FLAG), effectiveFlag);
    }

    public static Specification<FeatureMessage> greaterThanValidToDate(LocalDateTime validToDate) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThan(root.get(FeatureMessage_.VALID_TO_DATE), validToDate);
    }

    public static Specification<FeatureMessage> lessThanOrEqualToValidFromDate(LocalDateTime validFromDate) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get(FeatureMessage_.VALID_FROM_DATE), validFromDate);
    }
}
