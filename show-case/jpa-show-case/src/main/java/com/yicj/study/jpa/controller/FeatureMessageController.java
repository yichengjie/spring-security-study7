package com.yicj.study.jpa.controller;

import com.yicj.study.jpa.dto.FeatureMessageDTO;
import com.yicj.study.jpa.service.FeatureMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author yicj
 * @since 2024/9/6 21:59
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/feature-message")
public class FeatureMessageController {

    private final FeatureMessageService featureMessageService ;

    @PostMapping("/create")
    public String create(@RequestBody FeatureMessageDTO dto){
        FeatureMessageDTO retObj = featureMessageService.create(dto);
        return retObj.getId() ;
    }

    @PutMapping("/update")
    public Integer update(@RequestBody FeatureMessageDTO dto){
        return featureMessageService.update(dto);
    }
}
