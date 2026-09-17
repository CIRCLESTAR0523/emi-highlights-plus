package dev.circlestar.emihighlightsplus.client.priority;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

/** A menu-local baseline; a demand-only refresh does not evaluate the knowledge stream. */
public final class PriorityCandidateCache<T> {
    private List<T> baseline;
    private Object context;
    private Set<T> lastRequired;
    private List<T> ordered;

    public boolean canReplay(Object currentContext) {
        return baseline != null && Objects.equals(context, currentContext);
    }

    public void invalidate() {
        baseline = null;
        context = null;
        lastRequired = null;
        ordered = null;
    }

    public List<T> resolve(Object currentContext, boolean replay, Set<T> required, Supplier<List<T>> source) {
        if (!replay || !canReplay(currentContext)) {
            baseline = List.copyOf(source.get());
            context = currentContext;
            lastRequired = null;
        }
        if (!required.equals(lastRequired)) {
            lastRequired = Set.copyOf(required);
            ordered = StablePriorityOrder.partition(baseline, lastRequired);
        }
        return ordered;
    }
}
