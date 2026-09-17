package dev.circlestar.emihighlightsplus.client.priority;

import org.junit.jupiter.api.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

/** Checks fixed dependency bytecode without loading Minecraft classes or changing dependency JARs. */
class TransmutationMixinContractTest {
    @Test void pageSourceHasExactlyOneListMaterialization() throws IOException {
        var node = read("moze_intel/projecte/gameObjs/container/inventory/TransmutationInventory");
        var method = node.methods.stream().filter(m -> m.name.equals("updateClientTargets") && m.desc.equals("(J)V"))
                .findFirst().orElseThrow();
        int count = 0;
        for (var instruction : method.instructions) {
            if (instruction instanceof MethodInsnNode call && call.owner.equals("java/util/stream/Stream")
                    && call.name.equals("toList") && call.desc.equals("()Ljava/util/List;")) {
                count++;
            }
        }
        assertEquals(1, count, "Review the injection point when the dependency changes");
    }

    @Test void shadowAndInvalidationTargetsExist() throws IOException {
        var node = read("moze_intel/projecte/gameObjs/container/inventory/TransmutationInventory");
        for (String name : new String[]{"inputLocks", "outputs", "searchPage"}) {
            assertTrue(node.fields.stream().anyMatch(field -> field.name.equals(name)), name);
        }
        for (String name : new String[]{"itemLearned", "itemUnlearned", "getMaxDisplayedEmc"}) {
            assertTrue(node.methods.stream().anyMatch(method -> method.name.equals(name)), name);
        }
        var packet = read("moze_intel/projecte/network/packets/to_client/knowledge/KnowledgeSyncPKT");
        assertTrue(packet.methods.stream().anyMatch(method -> method.name.equals("handle")
                && method.desc.equals("(Lnet/neoforged/neoforge/network/handling/IPayloadContext;)V")));
    }

    private static ClassNode read(String name) throws IOException {
        try (var input = TransmutationMixinContractTest.class.getClassLoader().getResourceAsStream(name + ".class")) {
            assertNotNull(input, "Pinned test dependency missing: " + name);
            var node = new ClassNode();
            new ClassReader(input).accept(node, 0);
            return node;
        }
    }
}
