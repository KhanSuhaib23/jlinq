package com.snk.jlinq.data;

import com.snk.jlinq.function.MemberAccessor;
import com.snk.jlinq.function.MethodUtil;
import com.snk.jlinq.reflect.ReflectionUtil;
import com.snk.jlinq.tuple.*;

import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamContext {
    private final static List<Function<Object, Object>> baseMapper =
            Arrays.asList(
                    (Object t) -> ((Tuple1) t).v1(),
                    (Object t) -> ((Tuple2) t).v2(),
                    (Object t) -> ((Tuple3) t).v3()

            );

    private final Map<MemberAccessor, Function<Object, Object>> memberAccessMap;
    private final Map<StreamAlias, Function<Object, Object>> streamAliasMap;
    private final Map<MemberAccessor, Function<Object, Object>> groupMemberAccessor;

    private StreamContext(Map<StreamAlias, Function<Object, Object>> streamAliasMap, Map<MemberAccessor, Function<Object, Object>> memberAccessMap) {
        this.memberAccessMap = memberAccessMap;
        this.streamAliasMap = streamAliasMap;
        this.groupMemberAccessor = Collections.emptyMap();
    }


    public Function<Object, Object> get(MemberAccessor memberAccessor) {
        List<Pair<MemberAccessor, Function<Object, Object>>> l = memberAccessMap.entrySet().stream().map(Pair::of)
                .filter(p -> memberAccessor.streamAlias().canMatch(p.left().streamAlias()))
                .filter(p -> p.left().method().equals(memberAccessor.method()))
                .collect(Collectors.toList());

        if (l.size() == 0) {
            return get(memberAccessor.streamAlias(), memberAccessor.method());
        } else if (l.size() == 1) {
            return l.get(0).right();
        } else {
            return l.stream()
                    .filter(p -> p.left().streamAlias().name().isBlank())
                    .findFirst()
                    .map(Pair::right)
                    .map(f -> f.andThen(o -> ReflectionUtil.invoke(memberAccessor.method(), o)))
                    .orElseThrow(RuntimeException::new);
        }
    }

    public Function<Object, Object> get(StreamAlias alias, Method method) {
        List<Pair<StreamAlias, Function<Object, Object>>> l = streamAliasMap.entrySet().stream().map(Pair::of)
                .filter(p -> alias.canMatch(p.left()))
                .collect(Collectors.toList());

        if (l.size() == 0) {
            throw new RuntimeException("No stream provided with definition " + alias);
        } else if (l.size() == 1) {
            return l.get(0).right().andThen(o -> ReflectionUtil.invoke(method, o));
        } else {
            return l.stream()
                    .filter(p -> p.left().name().isBlank())
                    .findFirst()
                    .map(Pair::right)
                    .map(f -> f.andThen(o -> ReflectionUtil.invoke(method, o)))
                    .orElseThrow(RuntimeException::new);
        }
    }

    public static StreamContext init(Class<?> clazz) {
        return new StreamContext(Map.of(StreamAlias.of(clazz), Function.identity()), Collections.emptyMap());
    }

    public static StreamContext init(String alias, Class<?> clazz) {
        return new StreamContext(Map.of(StreamAlias.of(clazz, alias), Function.identity()), Collections.emptyMap());
    }

    public Class<?> classAt(int i) {
        return streamAliasMap.entrySet()
                .stream()
                .filter(e -> e.getValue().equals(i))
                .findFirst()
                .map(a -> a.getKey().clazz())
                .orElseThrow(RuntimeException::new);
    }


    public StreamContext merge(StreamContext otherContext) {
        int max = streamAliasMap.size();

        otherContext.streamAliasMap.keySet()
                .stream()
                .filter(k -> streamAliasMap.containsKey(k))
                .forEach(k -> {
                    throw new RuntimeException("Stream Alias " + k + " already exists in context");
                });

        Map<StreamAlias, Function<Object, Object>> newMap = new HashMap<>();

        if (streamAliasMap.size() == 1) {
            streamAliasMap.entrySet().stream()
                    .forEach(e -> newMap.put(e.getKey(), baseMapper.get(0)));
        } else {
            streamAliasMap.entrySet().stream()
                    .forEach(e -> newMap.put(e.getKey(), e.getValue()));
        }

        Iterator<StreamAlias> otherKeys = otherContext.streamAliasMap.keySet().iterator();

        while (otherKeys.hasNext()) {
            newMap.put(otherKeys.next(), baseMapper.get(max++));
        }

        return new StreamContext(newMap, Collections.emptyMap());
    }

    public StreamContext groupBy(List<MemberAccessor> groupBys) {
        if (groupBys.size() == 1) {
            return new StreamContext(Collections.emptyMap(), Map.of(groupBys.get(0), Function.identity()));
        }
        Map<MemberAccessor, Function<Object, Object>> memberAccessorFunctionMap = new HashMap<>();
        int i = 0;
        for (MemberAccessor groupBy : groupBys) {
            memberAccessorFunctionMap.put(groupBy, baseMapper.get(i));
            i++;
        }

        return new StreamContext(Collections.emptyMap(), memberAccessorFunctionMap);
    }
}
