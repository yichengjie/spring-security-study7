package com.yicj.study.jpa.service.impl;

import com.yicj.study.jpa.convertor.FeatureMessageConvertor;
import com.yicj.study.jpa.dto.FeatureMessageDTO;
import com.yicj.study.jpa.dto.PageInfo;
import com.yicj.study.jpa.dto.PageResponseData;
import com.yicj.study.jpa.entity.FeatureMessage;
import com.yicj.study.jpa.entity.FeatureMessage_;
import com.yicj.study.jpa.repository.FeatureMessageRepository;
import com.yicj.study.jpa.repository.specification.FeatureMessageSpecification;
import com.yicj.study.jpa.service.FeatureMessageService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    private final EntityManager entityManager ;

    @Override
    public List<FeatureMessage> findAll() {
        return repository.findAll();
    }

    @Override
    public FeatureMessageDTO create(FeatureMessageDTO dto) {
        FeatureMessage entity = FeatureMessageConvertor.I.toEntity(dto);
        entity.setId(UUID.randomUUID().toString());
        entity.setCreatedDate(LocalDateTime.now());
        entity.setCreatedBy("admin");
        entity.setLastModifiedBy("admin");
        entity.setLastModifiedDate(LocalDateTime.now());
        repository.save(entity);
        return FeatureMessageConvertor.I.toDto(entity);
    }

    @Override
    public int update(FeatureMessageDTO dto) {
        FeatureMessage entity = new FeatureMessage() ;
        entity.setId(dto.getId());
        entity.setMessageType(dto.getMessageType());
        entity.setDataPermission(dto.getDataPermission());
        entity.setMessageHeadline(dto.getMessageHeadline());
        entity.setSummary(dto.getSummary());
        entity.setMessageContent(dto.getMessageContent());
        entity.setLink(dto.getLink());
        entity.setCoverPageName(dto.getCoverPageName());
        entity.setCoverPageUrl(dto.getCoverPageUrl());
        entity.setAuthor(dto.getAuthor());
        entity.setValidFromDate(dto.getValidFromDate());
        entity.setValidToDate(dto.getValidToDate());
        entity.setLastModifiedBy("admin");
        entity.setLastModifiedDate(LocalDateTime.now());
        repository.save(entity);
        return 1;
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
                Sort.Order.desc(FeatureMessage_.LAST_MODIFIED_DATE),
                Sort.Order.desc(FeatureMessage_.CREATED_DATE)) ;
        Pageable pageable = PageRequest.of(pageNum, size, sort) ;
        //
        FeatureMessage param = new FeatureMessage();
        param.setActiveFlag(1);
        param.setValidFromDate(LocalDateTime.now()) ;
        param.setValidToDate(LocalDateTime.now());
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher(FeatureMessage_.ACTIVE_FLAG, match -> match.ignoreCase(true))
                .withMatcher(FeatureMessage_.SUMMARY, startsWith().ignoreCase());
        Example<FeatureMessage> example = Example.of(param, matcher);
        // 2. execute query
        Page<FeatureMessage> pageResult = repository.findAll(example, pageable);
        // 3. convert result
        return this.convertPageResponse(pageResult) ;
    }

    @Override
    public PageResponseData<FeatureMessageDTO> list4PageSpecification(int pageNum, int size) {
        Sort sort = Sort.by(
                Sort.Order.desc(FeatureMessage_.LAST_MODIFIED_DATE),
                Sort.Order.desc(FeatureMessage_.CREATED_DATE)) ;
        Pageable pageable = PageRequest.of(pageNum, size, sort) ;
        Specification<FeatureMessage> spec = (root, query, builder) ->{
            List<Predicate> predicateList = this.buildQueryPredicate(builder, root) ;
            // return query.where(predicateList.toArray(new Predicate[0])).getRestriction() ;
            return builder.and(predicateList.toArray(new Predicate[0])) ;
        } ;
        Page<FeatureMessage> pageResult = repository.findAll(spec, pageable);
        return this.convertPageResponse(pageResult) ;
    }



    @Override
    public PageResponseData<FeatureMessageDTO> list4PageSpecification2(int pageNum, int size) {
        Sort sort = Sort.by(
                Sort.Order.desc(FeatureMessage_.LAST_MODIFIED_DATE),
                Sort.Order.desc(FeatureMessage_.CREATED_DATE)) ;
        Pageable pageable = PageRequest.of(pageNum, size, sort) ;
        //
        LocalDateTime now = LocalDateTime.now() ;
        Specification<FeatureMessage> spec = Specification.where(null);
        spec = spec.and(FeatureMessageSpecification.eqActiveFlag(1));
        spec = spec.and(FeatureMessageSpecification.greaterThanValidToDate(now));
        spec = spec.and(FeatureMessageSpecification.lessThanOrEqualToValidFromDate(now));
        Page<FeatureMessage> pageResult = repository.findAll(spec, pageable);
        return this.convertPageResponse(pageResult) ;
    }

    @Override
    public PageResponseData<FeatureMessageDTO> list4PageSpecification3(int pageNum, int size) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<FeatureMessage> query = builder.createQuery(FeatureMessage.class);
        Root<FeatureMessage> root = query.from(FeatureMessage.class);
        //
        int offset = (pageNum - 1) * size ;
        List<Predicate> predicateList = this.buildQueryPredicate(builder, root);
        query.where(predicateList.toArray(new Predicate[0])) ;
        //
        List<FeatureMessage> resultList =
                entityManager.createQuery(query).setFirstResult(offset).setMaxResults(size).getResultList();
        return new PageResponseData<>(
                new PageInfo(pageNum, size, 0, 0), FeatureMessageConvertor.I.toDto(resultList)) ;
    }

    @Override
    public List<FeatureMessageDTO> listSpecification() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<FeatureMessage> query = builder.createQuery(FeatureMessage.class);
        Root<FeatureMessage> root = query.from(FeatureMessage.class);
        //
        List<Predicate> predicateList = this.buildQueryPredicate(builder, root);
        query.where(predicateList.toArray(new Predicate[0])) ;
        //
        List<FeatureMessage> resultList = entityManager.createQuery(query).getResultList();
        return FeatureMessageConvertor.I.toDto(resultList) ;
    }

    private List<Predicate> buildQueryPredicate(CriteriaBuilder builder, Root<FeatureMessage> root){
        List<Predicate> list = new ArrayList<>();
        // 生效中数据
        list.add(builder.equal(root.get(FeatureMessage_.ACTIVE_FLAG), "1")) ;
        // 生效开始日期小于等于当前日期
        list.add(builder.lessThanOrEqualTo(root.get(FeatureMessage_.VALID_FROM_DATE), LocalDateTime.now())) ;
        // 生效截至日期大于当前日期
        list.add(builder.greaterThan(root.get(FeatureMessage_.VALID_TO_DATE), LocalDateTime.now())) ;
        return list ;
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
