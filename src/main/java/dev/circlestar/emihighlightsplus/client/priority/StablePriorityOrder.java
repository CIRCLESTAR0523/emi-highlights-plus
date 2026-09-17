package dev.circlestar.emihighlightsplus.client.priority;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

/** Pure ordering helpers: never modify the source or reverse the priority group. */
public final class StablePriorityOrder {
    private StablePriorityOrder() {
    }

    public static <T> Comparator<T> comparator(Predicate<T> required, Comparator<T> original) {
        return Comparator.<T, Boolean>comparing(value -> !required.test(value)).thenComparing(original);
    }

    public static <T> List<T> partition(List<T> original, Set<T> required) {
        if (required.isEmpty() || original.size() < 2) {
            return original;
        }
        List<T> prioritized = new ArrayList<>();
        List<T> remaining = new ArrayList<>();
        for (T value : original) {
            (required.contains(value) ? prioritized : remaining).add(value);
        }
        if (prioritized.isEmpty() || remaining.isEmpty()) {
            return original;
        }
        prioritized.addAll(remaining);
        return List.copyOf(prioritized);
    }
}
