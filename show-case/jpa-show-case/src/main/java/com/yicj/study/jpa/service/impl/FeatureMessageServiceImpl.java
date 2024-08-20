package com.yicj.study.jpa.service.impl;

import com.yicj.study.jpa.convertor.FeatureMessageConvertor;
import com.yicj.study.jpa.dto.FeatureMessageDTO;
import com.yicj.study.jpa.dto.PageInfo;
import com.yicj.study.jpa.dto.PageResponseData;
import com.yicj.study.jpa.entity.FeatureMessage;
import com.yicj.study.jpa.repository.FeatureMessageRepository;
import com.yicj.study.jpa.repository.specification.FeatureMessageSpecification;
import com.yicj.study.jpa.service.FeatureMessageService;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.endsWith;
import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.startsWith;

/**
 * <p>
 * FeatureMessageServiceImpl
 * </p>
 *
 * @author yicj
 * @since 2024/08/18 19:24
 */
@Service
@RequiredArgsConstructor
public class FeatureMessageServiceImpl implements FeatureMessageService {

    private final FeatureMessageRepository repository ;

    @Override
    public List<FeatureMessage> findAll() {
        return repository.findAll();
    }

    @Override
    public FeatureMessage save(FeatureMessage entity) {
        return repository.save(entity);
    }

    @Override
    public List<FeatureMessage> batchSave(List<FeatureMessage> list) {
        return repository.saveAll(list);
    }

    // Example可以简化很多代码，但是功能有点弱，主要是对字符串匹配支持的比较好，对于非字符串的属性，只支持精确匹配
    // Example不支持范围查询（大于、小于、between等操作），这个就大大降低了Example的实用性。
    @Override
    public PageResponseData<FeatureMessageDTO> list4PageExample(int pageNum, int size) {
        // query all effective message order by last_modified_date desc and created_date desc
        // 1. assemble query param
        Sort sort = Sort.by(
                Sort.Order.desc("lastModifiedDate"),
                Sort.Order.desc("createdDate")) ;
        Pageable pageable = PageRequest.of(pageNum, size, sort) ;
        //
        FeatureMessage param = new FeatureMessage();
        param.setEffectiveFlag(1);
        param.setValidFromDate(LocalDateTime.now()) ;
        param.setValidToDate(LocalDateTime.now());
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("effectiveFlag", match -> match.ignoreCase(true))
                .withMatcher("summary", startsWith().ignoreCase());
        Example<FeatureMessage> example = Example.of(param, matcher);
        // 2. execute query
        Page<FeatureMessage> pageResult = repository.findAll(example, pageable);
        // 3. convert result
        return this.convertPageResponse(pageResult) ;
    }

    @Override
    public PageResponseData<FeatureMessageDTO> list4PageSpecification(int pageNum, int size) {
        Sort sort = Sort.by(
                Sort.Order.desc("lastModifiedDate"),
                Sort.Order.desc("createdDate")) ;
        Pageable pageable = PageRequest.of(pageNum, size, sort) ;
        Specification<FeatureMessage> spec = (root, query, builder) ->{
            List<Predicate> list = new ArrayList<>();
            // 生效中数据
            list.add(builder.equal(root.get("effectiveFlag"), "1")) ;
            // 生效开始日期小于等于当前日期
            list.add(builder.lessThanOrEqualTo(root.get("validFromDate"), LocalDateTime.now())) ;
            // 生效截至日期大于当前日期
            list.add(builder.greaterThan(root.get("validToDate"), LocalDateTime.now())) ;
            Predicate[] predicates = list.toArray(new Predicate[list.size()]);
            //return query.where(predicates).getRestriction() ;
            return builder.and(predicates) ;
        } ;
        Page<FeatureMessage> pageResult = repository.findAll(spec, pageable);
        return this.convertPageResponse(pageResult) ;
    }

    @Override
    public PageResponseData<FeatureMessageDTO> list4PageSpecification2(int pageNum, int size) {
        Sort sort = Sort.by(
                Sort.Order.desc("lastModifiedDate"),
                Sort.Order.desc("createdDate")) ;
        Pageable pageable = PageRequest.of(pageNum, size, sort) ;
        //
        LocalDateTime now = LocalDateTime.now() ;
        Specification<FeatureMessage> spec = Specification.where(null);
        spec = spec.and(FeatureMessageSpecification.eqEffectiveFlag(1));
        spec = spec.and(FeatureMessageSpecification.greaterThanValidToDate(now));
        spec = spec.and(FeatureMessageSpecification.lessThanOrEqualToValidFromDate(now));
        Page<FeatureMessage> pageResult = repository.findAll(spec, pageable);
        return this.convertPageResponse(pageResult) ;
    }

    private PageResponseData<FeatureMessageDTO> convertPageResponse(
            Page<FeatureMessage> pageResult){
        //
        PageInfo pageInfo = new PageInfo(
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalPages(),
                pageResult.getTotalElements()
        ) ;
        List<FeatureMessageDTO> retList = FeatureMessageConvertor.I.toDto(pageResult.getContent());
        return new PageResponseData<>(pageInfo, retList) ;
    }
}
