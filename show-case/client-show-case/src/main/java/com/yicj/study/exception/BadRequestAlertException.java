package com.yicj.study.exception;

import com.yicj.study.constants.ErrorConstants;
import com.yicj.study.constants.ErrorEnum;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class BadRequestAlertException extends AbstractThrowableProblem {

    private static final long serialVersionUID = 1L;

    private int code = ErrorEnum.BAD_REQUEST_ERROR.getCode();

    public BadRequestAlertException() {
        super(ErrorConstants.DEFAULT_TYPE, ErrorEnum.BAD_REQUEST_ERROR.getMessage(), Status.BAD_REQUEST);
    }

    public BadRequestAlertException(String message) {
        super(ErrorConstants.DEFAULT_TYPE, message, Status.BAD_REQUEST);
    }

    public BadRequestAlertException(ErrorEnum errorEnum) {
        super(ErrorConstants.DEFAULT_TYPE, errorEnum.getMessage(), Status.BAD_REQUEST);
        this.code = errorEnum.getCode();
    }

    public BadRequestAlertException(ErrorEnum errorEnum, String message) {
        super(ErrorConstants.DEFAULT_TYPE, message, Status.BAD_REQUEST);
        this.code = errorEnum.getCode();
    }

    public int getCode() {
        return code;
    }


}
