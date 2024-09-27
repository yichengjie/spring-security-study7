package com.yicj.study.r2dbc.exception;

import com.yicj.study.r2dbc.enums.Code;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class XException extends RuntimeException{
    private Code code;
    private int codeN;
    private String message;
}