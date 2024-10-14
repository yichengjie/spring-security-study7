package com.yicj.study.controller;

import com.yicj.core.exception.BadRequestAlertException;
import com.yicj.core.model.dto.IdListDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/attachment")
public class AttachmentController {

    @DeleteMapping("/attachments/{id}")
    //@ResponseStatus(HttpStatus.NO_CONTENT)
    public String deleteAttachment(@PathVariable String id) {
        log.info("REST request to delete Attachment: {}", id);
        return "delete attachment success" ;
    }

    @DeleteMapping("/attachments/batch")
    //@ResponseStatus(HttpStatus.NO_CONTENT)
    public String batchDeleteAttachment(@RequestBody IdListDTO dto) {
        log.info("REST request to batch delete Attachment: {}", dto.getIds());
        if (dto.isEmpty()) {
            throw new BadRequestAlertException("Invalid id");
        }
        return "batch delete attachment success" ;
    }

}
