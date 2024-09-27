package com.yicj.study.exception;

import com.yicj.study.constants.ErrorConstants;
import com.yicj.study.constants.ErrorEnum;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class PermissionDeniedException extends AbstractThrowableProblem {

    private static final long serialVersionUID = 1L;

    private int code = ErrorEnum.PERMISSION_DENIED_ERROR.getCode();

    public PermissionDeniedException() {
        super(ErrorConstants.DEFAULT_TYPE, ErrorEnum.PERMISSION_DENIED_ERROR.getMessage(), Status.FORBIDDEN);
    }

    public PermissionDeniedException(String message) {
        super(ErrorConstants.DEFAULT_TYPE, message, Status.FORBIDDEN);
    }

    public int getCode() {
        return code;
    }
}