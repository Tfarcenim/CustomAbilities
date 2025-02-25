package tfar.customabilities.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import tfar.customabilities.ModParticleTypes;

public class CustomAbilitiesClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ParticleFactoryRegistry.getInstance().register(ModParticleTypes.BUBBLE,ModBubbleParticle.Provider::new);
    }
}
