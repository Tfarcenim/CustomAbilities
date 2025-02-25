package tfar.customabilities;

import dev.lambdaurora.lambdynlights.api.DynamicLightHandlers;
import dev.lambdaurora.lambdynlights.api.DynamicLightsInitializer;
import net.minecraft.world.entity.EntityType;
import tfar.customabilities.platform.Services;

public class Lights implements DynamicLightsInitializer {
    @Override
    public void onInitializeDynamicLights() {
        DynamicLightHandlers.registerDynamicLightHandler(EntityType.PLAYER, Services.PLATFORM::getLightEmission);
    }
}
