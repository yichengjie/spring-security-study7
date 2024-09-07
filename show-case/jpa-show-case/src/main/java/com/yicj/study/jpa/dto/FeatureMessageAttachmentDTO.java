package com.yicj.study.jpa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yicj
 * @since 2024/9/7 7:50
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeatureMessageAttachmentDTO {

    // original file name
    private String originalFilename ;

    // full file path
    private String fullFilePath ;
}
