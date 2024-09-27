package com.yicj.study.r2dbc.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "user_react")
public class UserReact implements Persistable<String> {
    public static final String ROLE_USER = "hOl7U";
    public static final String ROLE_ADMIN = "yxp4r";

    @Id
    private String id;
    private String name;
    private String account;
    private String password;
    @JsonIgnore
    private String role;
    private LocalDateTime insertTime;
    private LocalDateTime updateTime;

    @Transient
    @Builder.Default
    private boolean newMessage = true;

    @Transient
    @Override
    public boolean isNew() {
        return newMessage || id == null;
    }
}