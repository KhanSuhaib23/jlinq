package com.snk.jlinq.stream.util;

import com.snk.jlinq.data.Condition;
import com.snk.jlinq.data.ConditionType;
import com.snk.jlinq.data.ConditionValue;
import com.snk.jlinq.stream.EnrichedStream;

import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class StreamFilter {
    // TODO for now everything is an and
    public static <T> EnrichedStream<T> streamFilter(EnrichedStream<T> stream, List<Condition> conditions) {

        Predicate<T> predicate = v -> {
            for (Condition<?> condition : conditions) {
                ConditionValue<?> l = condition.left();
                ConditionValue<?> r = condition.right();
                Object o1 = l.getValue(stream.context(), v);
                Object o2 = r.getValue(stream.context(), v);

                boolean t = getConditionFunction(condition.conditionType()).test(o1, o2);

                if (!t) {
                    return false;
                }
            }
            return true;
        };

        return new EnrichedStream<T>(stream.stream().filter(predicate), stream.context(), stream.orderedBy());
    }

    private static BiPredicate<Object, Object> getConditionFunction(ConditionType type) {
        switch (type) {
            case EQ:
                return (o1, o2) -> o1.equals(o2);
        }

        throw new RuntimeException("Found unsupported exception type");
    }
}
