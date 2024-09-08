package com.yicj.study.jpa.entity;

import com.yicj.study.jpa.dto.FeatureMessageAttachmentDTO;
import com.yicj.study.jpa.dto.convertor.FeatureMessageAttachmentConvertor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

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
public class FeatureMessage implements Serializable, Persistable<String> {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "message_type")
    private String messageType ;
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
    @Column(name = "attachments")
    @Convert(converter = FeatureMessageAttachmentConvertor.class)
    private List<FeatureMessageAttachmentDTO> attachments ;
    @Column(name = "valid_from_date")
    private Instant validFromDate ;
    @Column(name = "valid_to_date")
    private Instant validToDate ;
    // 0: not effective 1: effective
    @Column(name = "status")
    private Integer status ;
    // order by field
    @Column(name = "seq")
    private Integer seq ;
    // --------------common field----------------
    @Column(name = "created_by")
    private String createdBy ;
    @Column(name = "created_date")
    private Instant createdDate ;
    @Column(name = "last_modified_by")
    private String lastModifiedBy ;
    @Column(name = "last_modified_date")
    private Instant lastModifiedDate ;

    @Override
    public boolean isNew() {
        return false;
    }
}
