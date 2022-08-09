package com.snk.jlinq.data;

import java.util.Map;

public class StreamContext {
    private final Map<StreamAlias, Integer> streamAliasMap;

    private StreamContext(Map<StreamAlias, Integer> streamAliasMap) {
        this.streamAliasMap = streamAliasMap;
    }

    public Map<StreamAlias, Integer> streamAliasMap() {
        return streamAliasMap;
    }

    public Integer get(StreamAlias alias) {
        return streamAliasMap.get(alias);
    }

    public static StreamContext init(Class<?> clazz) {
        return new StreamContext(Map.of(StreamAlias.of(clazz), 1));
    }
}
