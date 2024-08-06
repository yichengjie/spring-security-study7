package com.yicj.study.model.builder;

import com.yicj.study.model.Address;
import com.yicj.study.model.Employee;
import com.yicj.study.model.Position;
import lombok.Data;

import java.util.*;

/**
 * <p>
 * EmployeeBuilder
 * </p>
 *
 * @author yicj
 * @since 2024年08月06日 13:01
 */
@Data
public class EmployeeBuilder {

    private String name;

    private Position position;

    private int salary;

    private int age;

    private Employee boss;

    private List<Employee> subordinates = new ArrayList<>();

    private Address primaryAddress;

    private Set<String> skills = new HashSet<>();

    public EmployeeBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public EmployeeBuilder withPosition(String position) {
        this.position = new Position(position);
        return this;
    }

    public EmployeeBuilder withSalary(int salary) {
        this.salary = salary;
        return this;
    }

    public EmployeeBuilder withAge(int age) {
        this.age = age ;
        return this ;
    }

    public EmployeeBuilder withBoss(Employee boss){
        this.boss = boss ;
        return this ;
    }

    public EmployeeBuilder withSubordinates(Employee ... subordinate){
        this.subordinates.addAll(Arrays.asList(subordinate));
        return this ;
    }

    public EmployeeBuilder withPrimaryAddress(Address primaryAddress){
        this.primaryAddress = primaryAddress ;
        return this ;
    }

    public EmployeeBuilder withSkills(String ... skills){
        this.skills.addAll(Arrays.asList(skills));
        return this ;
    }

    public static EmployeeBuilder Employee(String name) {
        return new EmployeeBuilder().withName(name);
    }

    public Employee build(){
        Employee employee = new Employee(this.name) ;
        employee.setPosition(this.position);
        employee.setSalary(this.salary);
        employee.setAge(this.age);
        employee.setBoss(this.boss);
        employee.setSubordinates(this.subordinates);
        employee.setPrimaryAddress(this.primaryAddress);
        employee.setSkills(this.skills);
        return employee ;
    }

}
