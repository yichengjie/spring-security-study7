package com.yicj.study.model;

import lombok.Data;

/**
 * <p>
 * Temperature
 * </p>
 *
 * @author yicj
 * @since 2024年07月28日 18:18
 */
@Data
public class Temperature {

    private final double value ;

    public Temperature(double value) {
        this.value = value;
    }
}
