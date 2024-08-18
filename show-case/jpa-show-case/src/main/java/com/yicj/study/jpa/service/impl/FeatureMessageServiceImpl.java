package com.yicj.study.jpa.service.impl;

import com.yicj.study.jpa.entity.FeatureMessage;
import com.yicj.study.jpa.repository.FeatureMessageRepository;
import com.yicj.study.jpa.service.FeatureMessageService;
import lombok.RequiredArgsConstructor;
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
}
