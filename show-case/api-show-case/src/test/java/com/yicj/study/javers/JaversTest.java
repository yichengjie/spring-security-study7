package com.yicj.study.javers;

import com.yicj.study.model.Address;
import com.yicj.study.model.Employee;
import com.yicj.study.model.Person;
import com.yicj.study.model.builder.EmployeeBuilder;
import lombok.extern.slf4j.Slf4j;
import org.javers.core.Changes;
import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.javers.core.diff.Diff;
import org.javers.core.diff.changetype.ValueChange;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.javers.core.diff.ListCompareAlgorithm.LEVENSHTEIN_DISTANCE;

/**
 * <p>
 * JaversTest
 * </p>
 *
 * @author yicj
 * @since 2024年08月06日 10:11
 */
@Slf4j
public class JaversTest {

    // https://javers.org/documentation/getting-started/
    @Test
    void hello1(){
        Javers javers = JaversBuilder.javers().build();
        Person tommyOld = new Person("tommy", "Tommy Smart");
        Person tommyNew = new Person("tommy", "Tommy C. Smart");
        Diff diff = javers.compare(tommyOld, tommyNew);
        Changes changes = diff.getChanges();
        changes.forEach(item -> {
            ValueChange valueChange = (ValueChange) item;
            log.info("propertyName: {}, left: {}, right: {}",
                    valueChange.getPropertyName(), valueChange.getLeft(), valueChange.getRight());
        });
    }

    @Test
    void commit(){
        Javers javers = JaversBuilder.javers().build();
        Person robert = new Person("bob", "Robert Martin");
        javers.commit("user", robert);
    }

    @Test
    void shouldCompareTwoEntities(){
        Javers javers = JaversBuilder.javers()
                //.withListCompareAlgorithm(LEVENSHTEIN_DISTANCE)
                .build();
        Employee frodoOld = EmployeeBuilder.Employee("Frodo")
                .withAge(40)
                .withPosition("Townsman")
                .withSalary(10_000)
                .withPrimaryAddress(new Address("Shire"))
                .withSkills("management")
                //.withSubordinates(new Employee("Sam"))
                .build();

        Employee frodoNew = EmployeeBuilder.Employee("Frodo")
                .withAge(41)
                .withPosition("Hero")
                //.withBoss(new Employee("Gandalf"))
                .withPrimaryAddress(new Address("Mordor"))
                .withSalary(12_000)
                .withSkills("management", "agile coaching")
                //.withSubordinates(new Employee("Sméagol"), new Employee("Sam"))
                .build();

        //when
        Diff diff = javers.compare(frodoOld, frodoNew);

        //then
        //Assertions.a(diff.getChanges()).hasSize(14);
        Changes changes = diff.getChanges();
        changes.forEach(item -> {
            //ValueChange valueChange = (ValueChange) item;
//            log.info("propertyName: {}, left: {}, right: {}",
//                    valueChange.getPropertyName(), valueChange.getLeft(), valueChange.getRight());
            log.info("item: {}", item) ;
        });
        //System.out.println(diff.prettyPrint());
    }
}
