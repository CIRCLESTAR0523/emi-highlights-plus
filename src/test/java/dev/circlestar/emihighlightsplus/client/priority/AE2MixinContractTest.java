package dev.circlestar.emihighlightsplus.client.priority;

import org.junit.jupiter.api.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/** Pins the AE2 19.2.17 internals used by the optional client Mixins. */
class AE2MixinContractTest {
    @Test void repoComparatorAndScrollSourceStillExist() throws IOException {
        var repo = read("appeng/client/gui/me/common/Repo");
        assertTrue(repo.fields.stream().anyMatch(field -> field.name.equals("src")
                && field.desc.equals("Lappeng/client/gui/widgets/IScrollSource;")));
        assertTrue(repo.methods.stream().anyMatch(method -> method.name.equals("getComparator")
                && method.desc.equals("(Lappeng/api/config/SortOrder;Lappeng/api/config/SortDir;)Ljava/util/Comparator;")));
    }

    @Test void normalViewUsesComparatorSeparatelyFromPinnedRow() throws IOException {
        var repo = read("appeng/client/gui/me/common/Repo");
        var updateView = repo.methods.stream().filter(method -> method.name.equals("updateView")
                && method.desc.equals("()V")).findFirst().orElseThrow();
        int comparatorCalls = 0;
        for (var instruction : updateView.instructions) {
            if (instruction instanceof MethodInsnNode call
                    && call.owner.equals("appeng/client/gui/me/common/Repo")
                    && call.name.equals("getComparator")) {
                comparatorCalls++;
            }
        }
        assertEquals(1, comparatorCalls, "Review normal-view sorting when AE2 changes");

        var screen = read("appeng/client/gui/me/common/MEStorageScreen");
        assertTrue(screen.fields.stream().anyMatch(field -> field.name.equals("repo")
                && field.desc.equals("Lappeng/client/gui/me/common/Repo;")));
    }

    @Test void terminalDecorationContractStillExists() throws IOException {
        var screen = read("appeng/client/gui/me/common/MEStorageScreen");
        assertTrue(screen.methods.stream().anyMatch(method -> method.name.equals("isViewOnlyCraftable")
                && method.desc.equals("()Z")));

        var slot = read("appeng/client/gui/me/common/RepoSlot");
        assertTrue(slot.methods.stream().anyMatch(method -> method.name.equals("getEntry")
                && method.desc.equals("()Lappeng/menu/me/common/GridInventoryEntry;")));

        var sizeRenderer = read("appeng/client/gui/me/common/StackSizeRenderer");
        assertTrue(sizeRenderer.methods.stream().anyMatch(method -> method.name.equals("renderSizeLabel")
                && method.desc.equals("(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/client/gui/Font;FFLjava/lang/String;Z)V")));
    }

    private static ClassNode read(String name) throws IOException {
        try (var input = AE2MixinContractTest.class.getClassLoader().getResourceAsStream(name + ".class")) {
            assertNotNull(input, "Pinned test dependency missing: " + name);
            var node = new ClassNode();
            new ClassReader(input).accept(node, 0);
            return node;
        }
    }
}
