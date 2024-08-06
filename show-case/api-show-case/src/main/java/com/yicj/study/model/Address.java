package com.yicj.study.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * <p>
 * Address
 * </p>
 *
 * @author yicj
 * @since 2024年08月06日 12:57
 */
@Data
public class Address {

    private String city;

    private String street;

    public Address(String city){
        this(city, null) ;
    }

    public Address(String city, String street){
        this.city = city ;
        this.street = street ;
    }
}
