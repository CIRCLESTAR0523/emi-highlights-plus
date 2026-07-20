package dev.circlestar.emicontainerhighlight;

import com.mojang.logging.LogUtils;
import dev.circlestar.emicontainerhighlight.client.ClientBootstrap;
import dev.circlestar.emicontainerhighlight.config.ClientConfig;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.loading.FMLEnvironment;
import org.slf4j.Logger;

@Mod(EmiContainerHighlights.MOD_ID)
public final class EmiContainerHighlights {
    public static final String MOD_ID = "emi_container_highlights";
    public static final Logger LOGGER = LogUtils.getLogger();

    public EmiContainerHighlights(IEventBus modEventBus, ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.CLIENT, ClientConfig.SPEC);

        if (FMLEnvironment.dist == Dist.CLIENT) {
            ClientBootstrap.initialize();
        }

        LOGGER.info("Loading EMI Container Highlights");
    }
}
