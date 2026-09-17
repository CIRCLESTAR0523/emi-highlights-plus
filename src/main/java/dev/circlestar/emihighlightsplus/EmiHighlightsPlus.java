package dev.circlestar.emihighlightsplus;

import com.mojang.logging.LogUtils;
import dev.circlestar.emihighlightsplus.client.ClientBootstrap;
import dev.circlestar.emihighlightsplus.config.ClientConfig;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.loading.FMLEnvironment;
import org.slf4j.Logger;

@Mod(EmiHighlightsPlus.MOD_ID)
public final class EmiHighlightsPlus {
    public static final String MOD_ID = "emi_highlights_plus";
    public static final Logger LOGGER = LogUtils.getLogger();

    public EmiHighlightsPlus(IEventBus modEventBus, ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.CLIENT, ClientConfig.SPEC);

        if (FMLEnvironment.dist == Dist.CLIENT) {
            ClientBootstrap.initialize();
        }

        LOGGER.info("Loading EMI Highlights Plus");
    }
}
