package com.yicj.study.jpa.convertor;

import java.util.List;

/**
 * <p>
 * BaseConvertor
 * </p>
 *
 * @author yicj
 * @since 2024/8/19 22:17
 */
public interface BaseConvertor<Entity, DTO>{

    DTO toDTO(Entity entity) ;

    List<DTO> toDTO(List<Entity> list) ;

    Entity toEntity(DTO dto) ;

    List<Entity> toEntity(List<DTO> list) ;
}
