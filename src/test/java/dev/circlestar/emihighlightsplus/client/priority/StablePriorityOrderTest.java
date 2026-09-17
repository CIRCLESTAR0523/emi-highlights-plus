package dev.circlestar.emihighlightsplus.client.priority;

import org.junit.jupiter.api.Test;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

class StablePriorityOrderTest {
    @Test void preservesOriginalOrderInBothGroups() {
        var input = List.of(9, 8, 7, 6, 5);
        assertEquals(List.of(8, 5, 9, 7, 6), StablePriorityOrder.partition(input, Set.of(5, 8)));
        assertEquals(List.of(9, 8, 7, 6, 5), input);
    }

    @Test void inactiveReturnsOriginal() {
        var input = List.of(4, 2, 1);
        assertSame(input, StablePriorityOrder.partition(input, Set.of()));
    }

    @Test void absentCandidatesAreNotInvented() {
        var input = List.of(4, 2, 1);
        assertSame(input, StablePriorityOrder.partition(input, Set.of(6)));
    }

    @Test void handlesEmptySingleAndAllRequired() {
        assertEquals(List.of(), StablePriorityOrder.partition(List.of(), Set.of(1)));
        assertEquals(List.of(1), StablePriorityOrder.partition(List.of(1), Set.of(1)));
        assertEquals(List.of(2, 1), StablePriorityOrder.partition(List.of(2, 1), Set.of(1, 2)));
    }

    @Test void preservesDuplicatesAndComponents() {
        record Entry(String item, String component) {}
        var a = new Entry("item", "a");
        var b = new Entry("item", "b");
        assertEquals(List.of(b, a, a), StablePriorityOrder.partition(List.of(a, b, a), Set.of(b)));
    }

    @Test void requiredStaysFirstInEitherDirection() {
        var required = Set.of(2, 4);
        var input = List.of(1, 2, 3, 4, 5);
        assertEquals(List.of(2, 4, 1, 3, 5), input.stream().sorted(
                StablePriorityOrder.comparator(required::contains, Comparator.<Integer>naturalOrder())).toList());
        assertEquals(List.of(4, 2, 5, 3, 1), input.stream().sorted(
                StablePriorityOrder.comparator(required::contains, Comparator.<Integer>reverseOrder())).toList());
    }

    @Test void paginatesAfterPromotion() {
        var input = java.util.stream.IntStream.range(0, 100).boxed().toList();
        var order = StablePriorityOrder.partition(input, Set.of(99, 60));
        assertEquals(List.of(60, 99, 0, 1), order.subList(0, 4));
        assertEquals(100, Set.copyOf(order).size());
    }
}
