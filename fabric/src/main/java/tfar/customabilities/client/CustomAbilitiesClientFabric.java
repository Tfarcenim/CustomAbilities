package tfar.customabilities.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import tfar.customabilities.ModParticleTypes;

public class CustomAbilitiesClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ParticleFactoryRegistry.getInstance().register(ModParticleTypes.BUBBLE,ModBubbleParticle.Provider::new);
        ClientTickEvents.START_CLIENT_TICK.register(CustomAbilitiesClient::tick);

        KeyBindingHelper.registerKeyBinding(ModKeybinds.PRIMARY);
        KeyBindingHelper.registerKeyBinding(ModKeybinds.SECONDARY);
        KeyBindingHelper.registerKeyBinding(ModKeybinds.TERTIARY);
        KeyBindingHelper.registerKeyBinding(ModKeybinds.QUATERNARY);
        KeyBindingHelper.registerKeyBinding(ModKeybinds.QUINARY);

        KeyBindingHelper.registerKeyBinding(ModKeybinds.CHANGE_PERCENT);
        CustomAbilitiesClient.renderers();
    }
}
