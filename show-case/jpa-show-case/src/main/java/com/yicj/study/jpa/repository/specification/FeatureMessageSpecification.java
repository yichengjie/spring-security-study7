package com.yicj.study.jpa.repository.specification;

import com.yicj.study.jpa.entity.FeatureMessage;
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

    public static Specification<FeatureMessage> eqEffectiveFlag(Integer effectiveFlag) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("effectiveFlag"), effectiveFlag);
    }

    public static Specification<FeatureMessage> greaterThanValidToDate(LocalDateTime validToDate) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThan(root.get("validToDate"), validToDate);
    }

    public static Specification<FeatureMessage> lessThanOrEqualToValidFromDate(LocalDateTime validFromDate) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("validFromDate"), validFromDate);
    }
}
