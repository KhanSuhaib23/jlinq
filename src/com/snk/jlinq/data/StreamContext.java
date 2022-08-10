package com.snk.jlinq.data;

import com.snk.jlinq.tuple.Pair;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamContext {
    private final Map<StreamAlias, Integer> streamAliasMap;

    private StreamContext(Map<StreamAlias, Integer> streamAliasMap) {
        this.streamAliasMap = streamAliasMap;
    }

    public Map<StreamAlias, Integer> streamAliasMap() {
        return streamAliasMap;
    }

    public Integer get(StreamAlias alias) {
        List<Pair<StreamAlias, Integer>> l = streamAliasMap.entrySet().stream().map(Pair::of)
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
        return new StreamContext(Map.of(StreamAlias.of(clazz), 1));
    }

    public static StreamContext init(String alias, Class<?> clazz) {
        return new StreamContext(Map.of(StreamAlias.of(clazz, alias), 1));
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
        Integer max = streamAliasMap.values().stream().max(Comparator.naturalOrder()).orElseThrow(RuntimeException::new);

        otherContext.streamAliasMap.keySet()
                .stream()
                .filter(k -> streamAliasMap.containsKey(k))
                .forEach(k -> {
                    throw new RuntimeException("Stream Alias " + k + " already exists in context");
                });

        Map<StreamAlias, Integer> newMap = Stream.concat(
                streamAliasMap.entrySet().stream().map(Pair::of),
                otherContext.streamAliasMap.entrySet().stream().map(Pair::of).map(p -> Pair.of(p.left(), p.right() + max))
        )
                .collect(Collectors.toMap(Pair::left, Pair::right));

        return new StreamContext(newMap);
    }
}
