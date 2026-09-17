package dev.circlestar.emihighlightsplus.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public final class ClientConfig {
    public static final ModConfigSpec SPEC;
    public static final ModConfigSpec.BooleanValue ENABLED;
    public static final ModConfigSpec.BooleanValue SOPHISTICATED_STORAGE_ENABLED;
    public static final ModConfigSpec.BooleanValue SOPHISTICATED_BACKPACKS_ENABLED;
    public static final ModConfigSpec.BooleanValue PROJECT_E_ENABLED;
    public static final ModConfigSpec.BooleanValue PROJECT_EXPANSION_ENABLED;
    public static final ModConfigSpec.BooleanValue AE2_ENABLED;
    public static final ModConfigSpec.BooleanValue REFINED_STORAGE_ENABLED;
    public static final ModConfigSpec.BooleanValue REQUIRED_MATERIAL_PRIORITY;
    public static final ModConfigSpec.BooleanValue DEBUG;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

        builder.push("client");
        ENABLED = builder
                .comment("Enables EMI Highlights Plus.")
                .define("enabled", true);
        SOPHISTICATED_STORAGE_ENABLED = builder
                .comment("Enables highlights in Sophisticated Storage screens.")
                .define("sophisticatedStorageEnabled", true);
        SOPHISTICATED_BACKPACKS_ENABLED = builder
                .comment("Enables highlights in Sophisticated Backpacks screens.")
                .define("sophisticatedBackpacksEnabled", true);
        PROJECT_E_ENABLED = builder
                .comment("Enables required-material priority in ProjectE Transmutation Table and Tablet screens.")
                .define("projectEEnabled", true);
        PROJECT_EXPANSION_ENABLED = builder
                .comment("Makes Arcane Transmutation Tablet materials independent of the visible page.")
                .define("projectExpansionEnabled", true);
        AE2_ENABLED = builder
                .comment("Enables required-material priority in Applied Energistics 2 storage terminals.")
                .define("ae2Enabled", true);
        REFINED_STORAGE_ENABLED = builder
                .comment("Enables required-material priority in Refined Storage grids.")
                .define("refinedStorageEnabled", true);
        REQUIRED_MATERIAL_PRIORITY = builder
                .comment("Prioritizes required materials in supported transmutation screens while EMI crafting mode is active.",
                        "Preserves the original order within each group, search, fuel slots and the lock slot.")
                .define("requiredMaterialPriority", true);
        DEBUG = builder
                .comment("Enables rate-limited compatibility debug logging.")
                .define("debug", false);
        builder.pop();

        SPEC = builder.build();
    }

    private ClientConfig() {
    }
}
