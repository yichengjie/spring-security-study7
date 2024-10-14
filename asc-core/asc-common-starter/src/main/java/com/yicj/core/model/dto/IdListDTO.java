package com.yicj.core.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IdListDTO {

    private List<String> ids;

    @JsonIgnore
    public boolean isEmpty() {
        List<String> filterList = this.getNoBlankIds();
        return filterList.isEmpty();
    }

    @JsonIgnore
    public List<String> getNoBlankIds() {
        if (ids == null || ids.isEmpty()){
            return Collections.emptyList();
        }
        return ids.stream()
            .filter(id -> id != null && !id.isEmpty())
            .collect(Collectors.toList());
    }
}