package com.yicj.study.exception;


import lombok.Getter;
import org.zalando.problem.AbstractThrowableProblem;

/**
 * @author yicj
 * @since 2024/9/27
 */
@Getter
public class OutOfCreditException extends AbstractThrowableProblem {

    private final int balance;

    public OutOfCreditException(int balance) {
        super(null, "You do not have enough credit.");
        this.balance = balance;
    }
}
