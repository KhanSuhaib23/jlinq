package com.snk.jlinq.stream;

import com.snk.jlinq.udt.*;
import com.snk.jlinq.util.MethodUtil;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class StreamContext {
    @SuppressWarnings("rawtypes")
    private final static List<Function<Object, Object>> baseMapper =
            Arrays.asList(
                    (Object t) -> ((Tuple1) t).v1(),
                    (Object t) -> ((Tuple2) t).v2(),
                    (Object t) -> ((Tuple3) t).v3()

            );

    private final Class<?> mainClass;
    private final List2<DataSelector, Function<Object, Object>> memberAccessMap;
    private final List2<StreamAlias, Function<Object, Object>> streamAliasMap;
    private final List2<StreamAlias, Function<Object, Object>> groupMemberAccessor;

    @SuppressWarnings("rawtypes")
    private static final Function<Object, Object> groupTypeAccessor = (Object p) -> ((Pair) p).left();

    private StreamContext(Class<?> mainClass, List2<DataSelector, Function<Object, Object>> memberAccessMap,
                          List2<StreamAlias, Function<Object, Object>> streamAliasMap,
                          List2<StreamAlias, Function<Object, Object>> groupMemberAccessor) {
        this.mainClass = mainClass;
        this.memberAccessMap = memberAccessMap;
        this.streamAliasMap = streamAliasMap;
        this.groupMemberAccessor = groupMemberAccessor;
    }

    private static StreamContext of(Class<?> mainClass, List2<DataSelector, Function<Object, Object>> memberAccessMap,
                                    List2<StreamAlias, Function<Object, Object>> groupMemberAccessor) {
        return new StreamContext(mainClass, memberAccessMap, List2.empty(), groupMemberAccessor);
    }

    private static StreamContext of(Class<?> mainClass, List2<StreamAlias, Function<Object, Object>> streamAliasMap) {
        return new StreamContext(mainClass, List2.empty(), streamAliasMap, List2.empty());
    }

    @SuppressWarnings("unchecked")
    public <IN, OUT> OUT extractValue(DataSelector selector, IN data) {
        return (OUT) get(selector).apply(data);
    }

    public Function<Object, Object> getAggregate(DataSelector selector) {
        return groupMemberAccessor.stream()
                .filter(m -> m.left().equals(selector.streamAlias()))
                .map(Pair::right)
                .map(f -> f.andThen(o -> MethodUtil.invoke(selector.method(), o)))
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }

    public Function<Object, Object> get(DataSelector selector) {
        List<Pair<DataSelector, Function<Object, Object>>> l = memberAccessMap.stream()
                .filter(p -> selector.streamAlias().canMatch(p.left().streamAlias()))
                .filter(p -> p.left().method().equals(selector.method())).toList();

        if (l.size() == 0) {
            return get(selector.streamAlias(), selector.method());
        } else if (l.size() == 1) {
            return groupTypeAccessor.andThen(l.get(0).right());
        } else {
            return l.stream()
                    .filter(p -> p.left().streamAlias().name().isBlank())
                    .findFirst()
                    .map(Pair::right)
                    .map(f -> groupTypeAccessor.andThen(f).andThen(o -> MethodUtil.invoke(selector.method(), o)))
                    .orElseThrow(RuntimeException::new);
        }
    }

    public Function<Object, Object> get(StreamAlias alias, Method method) {
        List<Pair<StreamAlias, Function<Object, Object>>> l = streamAliasMap.stream()
                .filter(p -> alias.canMatch(p.left())).toList();

        if (l.size() == 0) {
            throw new RuntimeException("No stream provided with definition " + alias);
        } else if (l.size() == 1) {
            return groupTypeAccessor.andThen(l.get(0).right()).andThen(o -> MethodUtil.invoke(method, o));
        } else {
            return l.stream()
                    .filter(p -> p.left().name().isBlank())
                    .findFirst()
                    .map(Pair::right)
                    .map(f -> groupTypeAccessor.andThen(f).andThen(o -> MethodUtil.invoke(method, o)))
                    .orElseThrow(RuntimeException::new);
        }
    }

    public static StreamContext init(Class<?> clazz) {
        return of(clazz, List2.of(StreamAlias.of(clazz), Function.identity()));
    }

    public static StreamContext init(String alias, Class<?> clazz) {
        return of(clazz, List2.of(StreamAlias.of(clazz, alias), Function.identity()));
    }

    public Class<?> classAt(int index) {
        if (index == 0 && streamAliasMap.size() == 1) {
            return streamAliasMap.get(index).left().clazz();
        } else {
            throw new RuntimeException("Right now only support index 0 (size 1) operations");
        }
    }


    public StreamContext merge(StreamContext otherContext) {
        Set<StreamAlias> keys = streamAliasMap.streamLeft().collect(Collectors.toSet());
        int max = streamAliasMap.size();

        otherContext.streamAliasMap.streamLeft()
                .filter(keys::contains)
                .forEach(k -> {
                    throw new RuntimeException("Stream Alias " + k + " already exists in context");
                });

        List2<StreamAlias, Function<Object, Object>> newList = new List2<>();

        if (streamAliasMap.size() == 1) {
            streamAliasMap.streamLeft()
                    .forEach(k -> newList.add(k, baseMapper.get(0)));
        } else {
            streamAliasMap
                    .forEach(e -> newList.add(e.left(), e.right()));
        }

        Iterator<StreamAlias> otherKeys = otherContext.streamAliasMap.iteratorLeft();

        while (otherKeys.hasNext()) {
            newList.add(otherKeys.next(), baseMapper.get(max++));
        }

        return of(mainClass, newList);
    }

    public StreamContext groupBy(List<DataSelector> groupBys) {

        if (groupBys.size() == 1) {
            return of(mainClass, List2.of(groupBys.get(0), Function.identity()), streamAliasMap);
        }
        List2<DataSelector, Function<Object, Object>> selectorFunctionMap = new List2<>();
        int i = 0;
        for (DataSelector groupBy : groupBys) {
            selectorFunctionMap.add(groupBy, baseMapper.get(i));
            i++;
        }

        return of(mainClass, selectorFunctionMap, streamAliasMap);
    }
}
