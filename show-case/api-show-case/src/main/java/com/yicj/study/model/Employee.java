package com.yicj.study.model;

import lombok.Data;
import org.javers.core.metamodel.annotation.Id;
import org.javers.core.metamodel.annotation.TypeName;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * Employee
 * </p>
 *
 * @author yicj
 * @since 2024年08月06日 12:56
 */
@Data
@TypeName("Employee")
public class Employee {
    @Id
    private String name;

    private Position position;

    private int salary;

    private int age;

    private Employee boss;

    private List<Employee> subordinates = new ArrayList<>();

    private Address primaryAddress;

    private Set<String> skills;

    public Employee(String name){
        this.name = name;
    }
}
