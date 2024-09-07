package com.yicj.study.jpa.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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
    private String id;
    private String messageType ;
    private String messageHeadline ;
    private String summary ;
    private String messageContent ;
    private String link ;
    private String coverPageName ;
    private String coverPageUrl ;
    private String author ;
    private List<FeatureMessageAttachmentDTO> attachments ;
    private LocalDate validFromDate ;
    private LocalDate validToDate ;
    // 0: not effective 1: effective
    private Integer status ;
    // order by field
    private Integer seq ;
    private Instant createdDate;
    private Instant lastModifiedDate;
}
