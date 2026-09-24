package dev.circlestar.emihighlightsplus.client.priority;

import org.junit.jupiter.api.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/** Pins the Refined Storage 2.0.9 internals used by the optional client Mixin. */
class RefinedStorageMixinContractTest {
    @Test void repositorySortContractStillExists() throws IOException {
        var repository = read("com/refinedmods/refinedstorage/api/resource/repository/ResourceRepositoryImpl");
        assertTrue(repository.fields.stream().anyMatch(field -> field.name.equals("sort")
                && field.desc.equals("Ljava/util/Comparator;")));
        assertTrue(repository.methods.stream().anyMatch(method -> method.name.equals("setSort")
                && method.desc.equals("(Ljava/util/Comparator;Lcom/refinedmods/refinedstorage/api/resource/repository/SortingDirection;)V")));
        assertTrue(repository.methods.stream().anyMatch(method -> method.name.equals("sort")
                && method.desc.equals("()V")));
    }

    @Test void gridResourceIdentityContractStillExists() throws IOException {
        var item = read("com/refinedmods/refinedstorage/common/grid/view/ItemGridResource");
        assertTrue(item.methods.stream().anyMatch(method -> method.name.equals("getItemResource")
                && method.desc.equals("()Lcom/refinedmods/refinedstorage/common/support/resource/ItemResource;")));
        var menu = read("com/refinedmods/refinedstorage/common/grid/AbstractGridContainerMenu");
        assertTrue(menu.methods.stream().anyMatch(method -> method.name.equals("getRepository")
                && method.desc.equals("()Lcom/refinedmods/refinedstorage/api/resource/repository/ResourceRepository;")));
    }

    @Test void gridHighlightRenderContractStillExists() throws IOException {
        var screen = read("com/refinedmods/refinedstorage/common/grid/screen/AbstractGridScreen");
        assertTrue(screen.methods.stream().anyMatch(method -> method.name.equals("renderResourceWithAmount")
                && method.desc.equals("(Lnet/minecraft/client/gui/GuiGraphics;IILcom/refinedmods/refinedstorage/common/api/grid/view/GridResource;)V")));
        assertTrue(screen.methods.stream().anyMatch(method -> method.name.equals("renderSlotBackground")
                && method.desc.equals("(Lnet/minecraft/client/gui/GuiGraphics;IIZI)V")));
        assertTrue(screen.methods.stream().anyMatch(method -> method.name.equals("renderAmount")
                && method.desc.equals("(Lnet/minecraft/client/gui/GuiGraphics;IILcom/refinedmods/refinedstorage/common/api/grid/view/GridResource;)V")));
    }

    private static ClassNode read(String name) throws IOException {
        try (var input = RefinedStorageMixinContractTest.class.getClassLoader().getResourceAsStream(name + ".class")) {
            assertNotNull(input, "Pinned test dependency missing: " + name);
            var node = new ClassNode();
            new ClassReader(input).accept(node, 0);
            return node;
        }
    }
}
