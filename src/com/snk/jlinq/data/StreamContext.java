package com.snk.jlinq.data;

import com.snk.jlinq.tuple.*;

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

    private final Map<StreamAlias, Function<Object, Object>> streamAliasMap;

    private StreamContext(Map<StreamAlias, Function<Object, Object>> streamAliasMap) {
        this.streamAliasMap = streamAliasMap;
    }

    public Function<Object, Object> get(StreamAlias alias) {
        List<Pair<StreamAlias, Function<Object, Object>>> l = streamAliasMap.entrySet().stream().map(Pair::of)
                .filter(p -> alias.canMatch(p.left()))
                .collect(Collectors.toList());

        if (l.size() == 0) {
            throw new RuntimeException("No stream provided with definition " + alias);
        } else if (l.size() == 1) {
            return l.get(0).right();
        } else {
            return l.stream()
                    .filter(p -> p.left().name().isBlank())
                    .findFirst()
                    .map(Pair::right)
                    .orElseThrow(RuntimeException::new);
        }
    }

    public static StreamContext init(Class<?> clazz) {
        return new StreamContext(Map.of(StreamAlias.of(clazz), Function.identity()));
    }

    public static StreamContext init(String alias, Class<?> clazz) {
        return new StreamContext(Map.of(StreamAlias.of(clazz, alias), Function.identity()));
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

        return new StreamContext(newMap);
    }
}
