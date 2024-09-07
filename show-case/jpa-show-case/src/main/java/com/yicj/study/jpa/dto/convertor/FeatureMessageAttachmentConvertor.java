package com.yicj.study.jpa.dto.convertor;

import com.yicj.study.jpa.dto.FeatureMessageAttachmentDTO;
import com.yicj.study.jpa.utils.JsonConverter;
import jakarta.persistence.AttributeConverter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author yicj
 * @since 2024/9/7 7:53
 */
@Slf4j
public class FeatureMessageAttachmentConvertor implements AttributeConverter<List<FeatureMessageAttachmentDTO>, String> {

    @Override
    public String convertToDatabaseColumn(List<FeatureMessageAttachmentDTO> attribute) {
        if (attribute == null || attribute.isEmpty()) {
            return null;
        }
        return JsonConverter.serializeObject(attribute);
    }

    @Override
    public List<FeatureMessageAttachmentDTO> convertToEntityAttribute(String dbData) {
        if (dbData != null) {
            return JsonConverter.deserializeObject(dbData, List.class, FeatureMessageAttachmentDTO.class);
        }
        return null;
    }
}
