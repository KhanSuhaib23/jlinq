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
    private final static List<Function<Object, Object>> baseMapper =
            Arrays.asList(
                    (Object t) -> ((Tuple1) t).v1(),
                    (Object t) -> ((Tuple2) t).v2(),
                    (Object t) -> ((Tuple3) t).v3()

            );

    private final Class<?> mainClass;
    private final List2<MemberAccessor, Function<Object, Object>> memberAccessMap;
    private final List2<StreamAlias, Function<Object, Object>> streamAliasMap;
    private final List2<StreamAlias, Function<Object, Object>> groupMemberAccessor;

    private static final Function<Object, Object> groupTypeAccessor = (Object p) -> ((Pair) p).left();

    private StreamContext(Class<?> mainClass, List2<MemberAccessor, Function<Object, Object>> memberAccessMap,
                          List2<StreamAlias, Function<Object, Object>> streamAliasMap,
                          List2<StreamAlias, Function<Object, Object>> groupMemberAccessor) {
        this.mainClass = mainClass;
        this.memberAccessMap = memberAccessMap;
        this.streamAliasMap = streamAliasMap;
        this.groupMemberAccessor = groupMemberAccessor;
    }

    private static StreamContext of(Class<?> mainClass, List2<MemberAccessor, Function<Object, Object>> memberAccessMap,
                                    List2<StreamAlias, Function<Object, Object>> groupMemberAccessor) {
        return new StreamContext(mainClass, memberAccessMap, List2.empty(), groupMemberAccessor);
    }

    private static StreamContext of(Class<?> mainClass, List2<StreamAlias, Function<Object, Object>> streamAliasMap) {
        return new StreamContext(mainClass, List2.empty(), streamAliasMap, List2.empty());
    }

    public Function<Object, Object> getAggregate(MemberAccessor memberAccessor) {
        return groupMemberAccessor.stream()
                .filter(m -> m.left().equals(memberAccessor.streamAlias()))
                .map(Pair::right)
                .map(f -> f.andThen(o -> MethodUtil.invoke(memberAccessor.method(), o)))
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }

    public Function<Object, Object> get(MemberAccessor memberAccessor) {
        List<Pair<MemberAccessor, Function<Object, Object>>> l = memberAccessMap.stream()
                .filter(p -> memberAccessor.streamAlias().canMatch(p.left().streamAlias()))
                .filter(p -> p.left().method().equals(memberAccessor.method()))
                .collect(Collectors.toList());

        if (l.size() == 0) {
            return get(memberAccessor.streamAlias(), memberAccessor.method());
        } else if (l.size() == 1) {
            return groupTypeAccessor.andThen(l.get(0).right());
        } else {
            return l.stream()
                    .filter(p -> p.left().streamAlias().name().isBlank())
                    .findFirst()
                    .map(Pair::right)
                    .map(f -> groupTypeAccessor.andThen(f).andThen(o -> MethodUtil.invoke(memberAccessor.method(), o)))
                    .orElseThrow(RuntimeException::new);
        }
    }

    public Function<Object, Object> get(StreamAlias alias, Method method) {
        List<Pair<StreamAlias, Function<Object, Object>>> l = streamAliasMap.stream()
                .filter(p -> alias.canMatch(p.left()))
                .collect(Collectors.toList());

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
                .filter(k -> keys.contains(k))
                .forEach(k -> {
                    throw new RuntimeException("Stream Alias " + k + " already exists in context");
                });

        List2<StreamAlias, Function<Object, Object>> newList = new List2<>();

        if (streamAliasMap.size() == 1) {
            streamAliasMap.streamLeft()
                    .forEach(k -> newList.add(k, baseMapper.get(0)));
        } else {
            streamAliasMap.stream()
                    .forEach(e -> newList.add(e.left(), e.right()));
        }

        Iterator<StreamAlias> otherKeys = otherContext.streamAliasMap.iteratorLeft();

        while (otherKeys.hasNext()) {
            newList.add(otherKeys.next(), baseMapper.get(max++));
        }

        return of(mainClass, newList);
    }

    public StreamContext groupBy(List<MemberAccessor> groupBys) {

        if (groupBys.size() == 1) {
            return of(mainClass, List2.of(groupBys.get(0), Function.identity()), streamAliasMap);
        }
        List2<MemberAccessor, Function<Object, Object>> memberAccessorFunctionMap = new List2<>();
        int i = 0;
        for (MemberAccessor groupBy : groupBys) {
            memberAccessorFunctionMap.add(groupBy, baseMapper.get(i));
            i++;
        }

        return of(mainClass, memberAccessorFunctionMap, streamAliasMap);
    }
}
