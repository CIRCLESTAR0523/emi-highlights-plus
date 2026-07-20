package dev.circlestar.emicontainerhighlight.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public final class ClientConfig {
    public static final ModConfigSpec SPEC;
    public static final ModConfigSpec.BooleanValue ENABLED;
    public static final ModConfigSpec.BooleanValue SOPHISTICATED_STORAGE_ENABLED;
    public static final ModConfigSpec.BooleanValue SOPHISTICATED_BACKPACKS_ENABLED;
    public static final ModConfigSpec.BooleanValue DEBUG;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

        builder.push("client");
        ENABLED = builder
                .comment("Enables EMI Container Highlights.")
                .define("enabled", true);
        SOPHISTICATED_STORAGE_ENABLED = builder
                .comment("Enables highlights in Sophisticated Storage screens.")
                .define("sophisticatedStorageEnabled", true);
        SOPHISTICATED_BACKPACKS_ENABLED = builder
                .comment("Enables highlights in Sophisticated Backpacks screens.")
                .define("sophisticatedBackpacksEnabled", true);
        DEBUG = builder
                .comment("Enables rate-limited compatibility debug logging.")
                .define("debug", false);
        builder.pop();

        SPEC = builder.build();
    }

    private ClientConfig() {
    }
}
