package com.yicj.study.jpa.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user_react")
public class UserReact implements Persistable<String> {
    public static final String ROLE_USER = "hOl7U";
    public static final String ROLE_ADMIN = "yxp4r";

    @Id
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    private String name;
    private String account;
    private String password;
    @JsonIgnore
    private String role;
    private LocalDateTime insertTime;
    private LocalDateTime updateTime;

    @Override
    public boolean isNew() {
        return false;
    }

}