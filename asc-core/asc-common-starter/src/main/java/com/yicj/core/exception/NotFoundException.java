package com.yicj.core.exception;

import com.yicj.core.common.ErrorConstants;
import com.yicj.core.common.ErrorEnum;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class NotFoundException extends AbstractThrowableProblem {

    private static final long serialVersionUID = 1L;

    private int code = ErrorEnum.NOT_FOUND_ERROR.getCode();

    public NotFoundException(String message) {
        super(ErrorConstants.DEFAULT_TYPE, message, Status.NOT_FOUND);
    }

    public NotFoundException(ErrorEnum errorEnum) {
        super(ErrorConstants.DEFAULT_TYPE, errorEnum.getMessage(), Status.NOT_FOUND);
        this.code = errorEnum.getCode();
    }

    public int getCode() {
        return code;
    }

}