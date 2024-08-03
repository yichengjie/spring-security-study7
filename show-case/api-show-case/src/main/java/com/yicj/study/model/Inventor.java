package com.yicj.study.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * <p>
 * Inventor
 * </p>
 *
 * @author yicj
 * @since 2024年08月03日 20:44
 */
@Data
public class Inventor {

    private String name;

    private List<Boolean> booleans ;

}
