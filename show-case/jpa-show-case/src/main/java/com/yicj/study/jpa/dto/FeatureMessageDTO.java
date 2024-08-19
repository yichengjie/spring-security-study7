package com.yicj.study.jpa.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * FeatureMessageDTO
 * </p>
 *
 * @author yicj
 * @since 2024/8/19 22:15
 */
@Data
public class FeatureMessageDTO implements Serializable {
    private String id ;
    private String messageType ;
    private String dataPermission ;
    private String messageHeadline ;
    private String summary ;
    private String messageContent ;
    private String link ;
    private String coverPageName ;
    private String coverPageUrl ;
    private String author ;
    private LocalDateTime validFromDate ;
    private LocalDateTime validToDate ;
    private String createdBy ;
    private LocalDateTime createdDate ;
    private String lastModifiedBy ;
    private LocalDateTime lastModifiedDate ;
    private Integer effectiveFlag ;
}
