package dev.circlestar.emihighlightsplus.client.projectexpansion;

import java.util.function.Predicate;

/** Does not reference optional GUI classes; safe when only ProjectE is installed. */
public final class ArcanePrioritySession {
    private static Predicate<Object> target = inventory -> false;
    private static long knowledgeRevision;

    private ArcanePrioritySession() {
    }

    public static void register(Predicate<Object> matcher) {
        target = target.or(matcher);
    }

    public static boolean matches(Object inventory) {
        return target.test(inventory);
    }

    public static long knowledgeRevision() {
        return knowledgeRevision;
    }

    public static void invalidateKnowledge() {
        knowledgeRevision++;
    }
}
