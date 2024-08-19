package com.yicj.study.jpa.convertor;

import com.yicj.study.jpa.dto.FeatureMessageDTO;
import com.yicj.study.jpa.entity.FeatureMessage;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * <p>
 * FeatureMessageConvertor
 * </p>
 *
 * @author yicj
 * @since 2024/8/19 22:17
 */
@Mapper
public interface FeatureMessageConvertor extends BaseConvertor<FeatureMessage, FeatureMessageDTO>{

    FeatureMessageConvertor I = Mappers.getMapper(FeatureMessageConvertor.class) ;
}
