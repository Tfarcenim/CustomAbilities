package tfar.customabilities.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.entity.event.v1.EntitySleepEvents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import tfar.customabilities.Abilities;
import tfar.customabilities.ModParticleTypes;
import tfar.customabilities.Utils;

public class CustomAbilitiesClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ParticleFactoryRegistry.getInstance().register(ModParticleTypes.BUBBLE,ModBubbleParticle.Provider::new);
        ClientTickEvents.START_CLIENT_TICK.register(CustomAbilitiesClient::tick);
        EntitySleepEvents.STOP_SLEEPING.register((entity, sleepingPos) -> {
            if (Utils.hasAbility(entity, Abilities.CUBONE)) {
                if (entity instanceof ServerPlayer player) {
                    player.addItem(new ItemStack(Items.EGG));
                }
            }
        });
        KeyBindingHelper.registerKeyBinding(ModKeybinds.PRIMARY);
        KeyBindingHelper.registerKeyBinding(ModKeybinds.SECONDARY);
        KeyBindingHelper.registerKeyBinding(ModKeybinds.TERTIARY);
        KeyBindingHelper.registerKeyBinding(ModKeybinds.QUATERNARY);
        CustomAbilitiesClient.renderers();
    }
}
