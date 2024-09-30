package com.yicj.core.exception;

import com.yicj.core.common.ErrorConstants;
import com.yicj.core.common.ErrorEnum;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class ServiceUnavailableException extends AbstractThrowableProblem {

    private static final long serialVersionUID = 1L;

    private int code = ErrorEnum.SERVICE_UNAVAILABLE_ERROR.getCode();

    public ServiceUnavailableException() {
        super(ErrorConstants.DEFAULT_TYPE, ErrorEnum.SERVICE_UNAVAILABLE_ERROR.getMessage(), Status.SERVICE_UNAVAILABLE);
    }

    public ServiceUnavailableException(String message) {
        super(ErrorConstants.DEFAULT_TYPE, message, Status.SERVICE_UNAVAILABLE);
    }

    public ServiceUnavailableException(ErrorEnum errorEnum) {
        super(ErrorConstants.DEFAULT_TYPE, errorEnum.getMessage(), Status.SERVICE_UNAVAILABLE);
        this.code = errorEnum.getCode();
    }

    public ServiceUnavailableException(ErrorEnum errorEnum, String message) {
        super(ErrorConstants.DEFAULT_TYPE, message, Status.SERVICE_UNAVAILABLE);
        this.code = errorEnum.getCode();
    }

    public int getCode() {
        return code;
    }


}