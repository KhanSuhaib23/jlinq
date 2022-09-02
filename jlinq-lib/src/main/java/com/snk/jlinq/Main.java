package com.snk.jlinq;

import com.snk.jlinq.udt.Tuple3;

import java.util.List;
import java.util.stream.Stream;

import static com.snk.jlinq.api.AggregationFunction.count;
import static com.snk.jlinq.api.AggregationFunction.list;
import static com.snk.jlinq.api.JLinq.from;

public class Main {

    private record Employee(Integer id, String name, Integer deptId, Integer managerId) {
    }

    private record Department(Integer id, String name) {

    }

    public static void main(String[] args) {

        List<Employee> employees = Stream.of(
                new Employee(1, "ABC", 1, 0),
                new Employee(2, "PQR", 2, 0),
                new Employee(3, "STU", 1, 0),
                new Employee(4, "XYZ", 2, 1),
                new Employee(5, "NAN", 2, 2),
                new Employee(6, "TEN", 1, 2),
                new Employee(7, "PAK", 3, 1),
                new Employee(8, "ABC2", 1, 3),
                new Employee(9, "PQR2", 2, 3),
                new Employee(10, "STU2", 1, 1),
                new Employee(11, "XYZ2", 2, 2),
                new Employee(12, "NAN2", 2, 2),
                new Employee(13, "TEN2", 1, 2),
                new Employee(14, "PAK2", 3, 3)
        ).toList();

        List<Department> departments = Stream.of(
                new Department(1, "COMP"),
                new Department(2, "IT"),
                new Department(3, "ELECTRONIC")
        ).toList();

        @SuppressWarnings("unused")
        List<Tuple3<String, String, String>> t1 =
                from(employees.stream(), Employee.class)
                .join("d", departments.stream(), Department.class)
                    .on(Employee::deptId).eq(Department::id)
                .join("m", employees.stream(), Employee.class)
                    .on(Employee::managerId).eq("m", Employee::id)
                .orderBy(Employee::name).then("m", Employee::name)
                .where(Department::name).eq("COMP")
                .select(Employee::name).comma(Department::name).comma("m", Employee::name)
                .toList();

        System.out.println();

        @SuppressWarnings("unused")
        List<Tuple3<Long, List<String>, Long>> t2 =
                from(employees.stream(), Employee.class)
                    .join("d", departments.stream(), Department.class)
                        .on(Employee::deptId).eq(Department::id)
                    .groupBy(Department::name).comma(Department::id)
                    .orderBy(Department::id)
                    .where(Department::id).eq(2)
                    .select(count(Employee::id))
                        .comma(list(Employee::name)).comma(count(Employee::name))
                    .toList();

        System.out.println();

    }
}
