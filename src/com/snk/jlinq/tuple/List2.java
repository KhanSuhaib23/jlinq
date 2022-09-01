package com.snk.jlinq.tuple;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.stream.Stream;

public class List2<T1, T2> extends ArrayList<Pair<T1, T2>> {
    private static List2 EMPTY_LIST = new List2<>();

    public Stream<T1> streamLeft() {
        return stream().map(Pair::left);
    }

    public Stream<T2> streamRight() {
        return stream().map(Pair::right);
    }

    public Iterator<T1> iteratorLeft() {
        return streamLeft().iterator();
    }

    public boolean add(T1 left, T2 right) {
        return add(Pair.of(left, right));
    }

    public static <T1, T2> List2<T1, T2> empty() {
        return EMPTY_LIST;
    }

    public static <T1, T2> List2<T1, T2> of(T1 l1, T2 r1) {
        List2<T1, T2> list = new List2<>();

        list.add(l1, r1);

        return list;
    }
}
