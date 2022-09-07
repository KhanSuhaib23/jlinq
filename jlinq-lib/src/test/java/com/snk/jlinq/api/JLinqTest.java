package com.snk.jlinq.api;

import com.snk.jlinq.udt.Tuple2;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class JLinqTest {
    record Employee(Integer id, String name, Integer deptId) {}
    record Department(Integer id, String name) {}


    @Test
    public void test() {
        List<Employee> employeeList = Arrays.asList(new Employee(1, "ABC", 1),
                new Employee(2, "PQR", 1),
                new Employee(3, "RTS", 2),
                new Employee(4, "UVW", 2),
                new Employee(5, "XYZ", 1));

        List<Department> departmentList = Arrays.asList(
                new Department(1, "COMP"),
                new Department(2, "IT")
        );

        List<Tuple2<Employee, Department>> expected = Arrays.asList(
                new Tuple2<>(employeeList.get(0), departmentList.get(0)),
                new Tuple2<>(employeeList.get(1), departmentList.get(0)),
                new Tuple2<>(employeeList.get(2), departmentList.get(1)),
                new Tuple2<>(employeeList.get(3), departmentList.get(1)),
                new Tuple2<>(employeeList.get(4), departmentList.get(0))
        );

        Stream<Employee> employeeStream = employeeList.stream();
        Stream<Department> departmentStream = departmentList.stream();

        List<Tuple2<Employee, Department>> res = JLinq.from(employeeStream, Employee.class)
                        .join("d", departmentStream, Department.class)
                            .on(Employee::deptId).eq(Department::id)
                        .toList();

        assertEquals(res, expected);
    }
}
