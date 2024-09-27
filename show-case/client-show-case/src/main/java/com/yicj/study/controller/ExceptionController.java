package com.yicj.study.controller;

import com.yicj.study.exception.BadRequestAlertException;
import com.yicj.study.exception.NotFoundException;
import com.yicj.study.exception.PermissionDeniedException;
import com.yicj.study.exception.ServiceUnavailableException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ExceptionController {

    @GetMapping("/test/internal-server-error")
    public void internalServerError() {
        throw new NullPointerException();
    }

    @GetMapping("/test/not-found-error")
    public void notFoundError() {
        throw new NotFoundException("not found");
    }

    @GetMapping("/test/bad-request-error")
    public void testBadRequestError() {
        throw new BadRequestAlertException("bad request");
    }

    @PostMapping("/test/validate-error")
    public void testValidateError(@Valid @RequestBody TestDTO testDTO) {
    }

    @GetMapping("/test/permission-denied-error")
    public void testPermissionDeniedError() {
        throw new PermissionDeniedException();
    }

    @GetMapping("/test/service-unavailable")
    public void testServiceUnavailableError() {
        throw new ServiceUnavailableException("cdp service is not available, please try again later. Sorry for the inconvenience.");
    }

    public static class TestDTO {

        @NotNull
        private String test;

        public String getTest() {
            return test;
        }

        public void setTest(String test) {
            this.test = test;
        }
    }
}
