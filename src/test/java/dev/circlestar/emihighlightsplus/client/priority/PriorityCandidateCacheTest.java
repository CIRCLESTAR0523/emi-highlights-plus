package dev.circlestar.emihighlightsplus.client.priority;

import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import static org.junit.jupiter.api.Assertions.*;

class PriorityCandidateCacheTest {
    private static final Supplier<List<Integer>> MUST_NOT_RESOLVE = () -> {
        throw new AssertionError("Demand-only refresh must not enumerate knowledge or resolve EMC");
    };

    @Test void demandChangesReuseBaselineAndDisableRestoresIt() {
        var cache = new PriorityCandidateCache<Integer>();
        cache.resolve("context", false, Set.of(), () -> List.of(5, 4, 3, 2, 1));
        assertEquals(List.of(3, 1, 5, 4, 2), cache.resolve("context", true, Set.of(1, 3), MUST_NOT_RESOLVE));
        assertEquals(List.of(2, 5, 4, 3, 1), cache.resolve("context", true, Set.of(2), MUST_NOT_RESOLVE));
        assertEquals(List.of(5, 4, 3, 2, 1), cache.resolve("context", true, Set.of(), MUST_NOT_RESOLVE));
    }

    @Test void unchangedMembershipReusesOrderedList() {
        var cache = new PriorityCandidateCache<Integer>();
        var order = cache.resolve("context", false, Set.of(1), () -> List.of(3, 2, 1));
        assertSame(order, cache.resolve("context", true, Set.of(1), MUST_NOT_RESOLVE));
    }

    @Test void changedContextCannotReplay() {
        var cache = new PriorityCandidateCache<Integer>();
        var calls = new AtomicInteger();
        Supplier<List<Integer>> source = () -> { calls.incrementAndGet(); return List.of(3, 2, 1); };
        cache.resolve(List.of(100, "lock", 1), false, Set.of(1), source);
        cache.resolve(List.of(50, "lock", 1), true, Set.of(1), source);
        cache.resolve(List.of(50, "changed-lock", 1), true, Set.of(1), source);
        cache.resolve(List.of(50, "changed-lock", 2), true, Set.of(1), source);
        assertEquals(4, calls.get());
    }

    @Test void sameSizeKnowledgeReplacementInvalidates() {
        var cache = new PriorityCandidateCache<Integer>();
        cache.resolve(1, false, Set.of(2), () -> List.of(3, 2, 1));
        cache.invalidate();
        assertFalse(cache.canReplay(1));
        assertEquals(List.of(3, 1, 0), cache.resolve(1, true, Set.of(2), () -> List.of(3, 1, 0)));
    }

    @Test void normalRefreshAlwaysUsesFreshCandidates() {
        var cache = new PriorityCandidateCache<Integer>();
        cache.resolve(1, false, Set.of(2), () -> List.of(3, 2, 1));
        assertEquals(List.of(4, 1), cache.resolve(1, false, Set.of(2), () -> List.of(4, 1)));
    }

    @Test void sourceIsSnapshottedAndRequiredSetIsCopied() {
        var cache = new PriorityCandidateCache<Integer>();
        var source = new java.util.ArrayList<>(List.of(3, 2, 1));
        var required = new java.util.HashSet<>(Set.of(1));
        cache.resolve(1, false, required, () -> source);
        source.clear();
        required.add(2);
        assertEquals(List.of(2, 1, 3), cache.resolve(1, true, required, MUST_NOT_RESOLVE));
    }

    @Test void largeRepeatedDemandChangesDoNotResolveSourceAgain() {
        var cache = new PriorityCandidateCache<Integer>();
        var source = java.util.stream.IntStream.range(0, 100_000).boxed().toList();
        cache.resolve(1, false, Set.of(), () -> source);
        for (int index = 0; index < 20; index++) {
            var ordered = cache.resolve(1, true, Set.of(99_999 - index), MUST_NOT_RESOLVE);
            assertEquals(99_999 - index, ordered.getFirst());
            assertEquals(source.size(), ordered.size());
        }
    }
}
