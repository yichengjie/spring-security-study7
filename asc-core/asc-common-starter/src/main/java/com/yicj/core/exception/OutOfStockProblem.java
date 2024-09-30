package com.yicj.core.exception;

import lombok.Getter;
import org.zalando.problem.AbstractThrowableProblem;

import javax.annotation.concurrent.Immutable;
import java.net.URI;

import static java.text.MessageFormat.format;
import static org.zalando.problem.Status.BAD_REQUEST;

@Getter
@Immutable
public final class OutOfStockProblem extends AbstractThrowableProblem {

    static final URI TYPE = URI.create("https://example.org/out-of-stock");

    private final String product;

    public OutOfStockProblem(final String product) {
        super(TYPE, "Out of Stock", BAD_REQUEST, format("Item %s is no longer available", product));
        this.product = product;
    }

}