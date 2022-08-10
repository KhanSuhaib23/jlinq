package com.snk.jlinq;

import com.snk.jlinq.stream.EnrichedStream;
import com.snk.jlinq.stream.JLinq;
import com.snk.jlinq.stream.SelectStream;
import com.snk.jlinq.tuple.Pair;
import com.snk.jlinq.tuple.Tuple2;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    private record Employee(Integer id, String name, Integer deptId) {
    }

    private record Department(Integer id, String name) {

    }

    public static void main(String[] args) {

        Stream<Employee> employees = Stream.of(
                new Employee(1, "ABC", 1),
                new Employee(2, "PQR", 2),
                new Employee(3, "STU", 1),
                new Employee(4, "XYZ", 2),
                new Employee(5, "NAN", 2),
                new Employee(6, "TEN", 1),
                new Employee(7, "PAK", 3),
                new Employee(8, "ABC2", 1),
                new Employee(9, "PQR2", 2),
                new Employee(10, "STU2", 1),
                new Employee(11, "XYZ2", 2),
                new Employee(12, "NAN2", 2),
                new Employee(13, "TEN2", 1),
                new Employee(14, "PAK2", 3)
        );

        List<Tuple2<Integer, String>> t = JLinq.from(employees, Employee.class)
                .where(Employee::deptId).eq(1)
                .or(Employee::deptId).eq(2)
                .select(Employee::id)
                .comma(Employee::name)
                .collect(Collectors.toList());

        System.out.println();

    }
}
