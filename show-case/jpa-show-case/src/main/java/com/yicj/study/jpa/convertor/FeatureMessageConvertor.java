package com.yicj.study.jpa.convertor;

import com.yicj.study.jpa.dto.FeatureMessageDTO;
import com.yicj.study.jpa.entity.FeatureMessage;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.time.*;

/**
 * <p>
 * FeatureMessageConvertor
 * </p>
 *
 * @author yicj
 * @since 2024/8/19 22:17
 */
@Mapper(
        //null ignore
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        //check null value
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        //ignore mapping not mapping field
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface FeatureMessageConvertor extends BaseConvertor<FeatureMessage, FeatureMessageDTO>{

    FeatureMessageConvertor I = Mappers.getMapper(FeatureMessageConvertor.class) ;

    @Mappings({
            @Mapping(target = "validFromDate", expression = "java(toStartLocalDataTime(dto.getValidFromDate()))"),
            @Mapping(target = "validToDate", expression = "java(toEndLocalDataTime(dto.getValidToDate()))"),
    })
    FeatureMessage toEntity(FeatureMessageDTO dto);

    @Mappings({
            @Mapping(target = "validFromDate", expression = "java(toLocalData(entity.getValidFromDate()))"),
            @Mapping(target = "validToDate", expression = "java(toLocalData(entity.getValidToDate()))"),
    })
    FeatureMessageDTO toDto(FeatureMessage entity);

    default Instant toStartLocalDataTime(LocalDate localDate){
        if (localDate == null){
            return null;
        }
        LocalDateTime localDateTime = LocalDateTime.of(localDate, LocalTime.of(0, 0, 0));
        return localDateTime.toInstant(ZoneOffset.UTC);
    }

    default Instant toEndLocalDataTime(LocalDate localDate){
        if (localDate == null){
            return null;
        }
        LocalDateTime localDateTime = LocalDateTime.of(localDate, LocalTime.of(23, 59, 59));
        return localDateTime.toInstant(ZoneOffset.UTC);
    }

    default LocalDate toLocalData(Instant localDate){
        if (localDate == null){
            return null;
        }
        return LocalDateTime.ofInstant(localDate, ZoneId.of("UTC")).toLocalDate();
    }
}
