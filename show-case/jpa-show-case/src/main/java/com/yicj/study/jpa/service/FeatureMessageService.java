package com.yicj.study.jpa.service;

import com.yicj.study.jpa.dto.FeatureMessageDTO;
import com.yicj.study.jpa.dto.PageResponseData;
import com.yicj.study.jpa.entity.FeatureMessage;

import java.util.List;

/**
 * <p>
 * FeatureMessageService
 * </p>
 *
 * @author yicj
 * @since 2024/08/18 19:24
 */
public interface FeatureMessageService {

    List<FeatureMessage> findAll() ;

    FeatureMessage save(FeatureMessage entity) ;

    List<FeatureMessage> batchSave(List<FeatureMessage> list) ;

    PageResponseData<FeatureMessageDTO> list4PageExample(int pageNum, int size);

    PageResponseData<FeatureMessageDTO> list4PageSpecification(int pageNum, int size);

    PageResponseData<FeatureMessageDTO> list4PageSpecification2(int pageNum, int size);

    PageResponseData<FeatureMessageDTO> list4PageSpecification3(int pageNum, int size);

    List<FeatureMessageDTO> listSpecification();
}
