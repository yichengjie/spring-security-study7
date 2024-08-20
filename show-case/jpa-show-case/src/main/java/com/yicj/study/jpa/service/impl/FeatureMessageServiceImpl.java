package com.yicj.study.jpa.service.impl;

import com.yicj.study.jpa.convertor.FeatureMessageConvertor;
import com.yicj.study.jpa.dto.FeatureMessageDTO;
import com.yicj.study.jpa.dto.PageInfo;
import com.yicj.study.jpa.dto.PageResponseData;
import com.yicj.study.jpa.entity.FeatureMessage;
import com.yicj.study.jpa.repository.FeatureMessageRepository;
import com.yicj.study.jpa.service.FeatureMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public PageResponseData<FeatureMessageDTO> list4Page(int pageNum, int size) {
        // query all effective message order by last_modified_date desc and created_date desc
        // 1. assemble query param
        Sort sort = Sort.by(
                Sort.Order.desc("lastModifiedDate"),
                Sort.Order.desc("createdDate")) ;
        Pageable pageable = PageRequest.of(pageNum, size, sort) ;
        //
        FeatureMessage param = new FeatureMessage();
        param.setEffectiveFlag(1);
        Example<FeatureMessage> example = Example.of(param);
        // 2. execute query
        Page<FeatureMessage> pageResult = repository.findAll(example, pageable);
        // 3. convert result
        return this.convertPageResponse(pageResult) ;
    }

    private PageResponseData<FeatureMessageDTO> convertPageResponse(Page<FeatureMessage> pageResult){
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
