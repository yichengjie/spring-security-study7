package com.yicj.study.model.builder;

import com.yicj.study.model.Inventor;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * InventorBuilder
 * </p>
 *
 * @author yicj
 * @since 2024年08月03日 20:48
 */
public class InventorBuilder {

    private String name;
    private List<Boolean> booleans = new ArrayList<>();

    public InventorBuilder name(String name) {
        this.name = name;
        return this;
    }

    public InventorBuilder booleans(List<Boolean> booleans) {
        this.booleans = booleans;
        return this;
    }

    public static InventorBuilder builder(){
        return new InventorBuilder() ;
    }
    public Inventor build() {
        Inventor inventor = new Inventor();
        inventor.setName(name);
        inventor.setBooleans(booleans);
        return inventor;
    }
}
