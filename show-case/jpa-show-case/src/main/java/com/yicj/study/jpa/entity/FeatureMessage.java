package com.yicj.study.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * <p>
 * FeatureMessage
 * </p>
 *
 * @author yicj
 * @since 2024/08/18 16:00
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "feature_message")
public class FeatureMessage {
    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    private String id ;
    @Column(name = "message_type")
    private String messageType ;
    @Column(name = "data_permission")
    private String dataPermission ;
    @Column(name = "message_headline")
    private String messageHeadline ;
    @Column(name = "summary")
    private String summary ;
    @Column(name = "message_content")
    private String messageContent ;
    @Column(name = "link")
    private String link ;
    @Column(name = "cover_page_name")
    private String coverPageName ;
    @Column(name = "cover_page_url")
    private String coverPageUrl ;
    @Column(name = "author")
    private String author ;
    @Column(name = "valid_from_date")
    private LocalDateTime validFromDate ;
    @Column(name = "valid_to_date")
    private LocalDateTime validToDate ;
    @Column(name = "created_by")
    private String createdBy ;
    @Column(name = "created_date")
    private LocalDateTime createdDate ;
    @Column(name = "last_modified_by")
    private String lastModifiedBy ;
    @Column(name = "last_modified_date")
    private LocalDateTime lastModifiedDate ;
    @Column(name = "active_flag")
    private Integer activeFlag ;

}
