package tfar.customabilities;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.entity.event.v1.EntityElytraEvents;
import net.fabricmc.fabric.api.entity.event.v1.EntitySleepEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import tfar.customabilities.ability.NewAbility;
import tfar.customabilities.attachments.CommonDataAttachments;
import tfar.customabilities.init.*;
import tfar.customabilities.network.client.S2CSyncAbilityPacket;
import tfar.customabilities.platform.Services;

public class CustomAbilitiesFabric implements ModInitializer {
    
    @Override
    public void onInitialize() {
        
        // This method is invoked by the Fabric mod loader when it is ready
        // to load your mod. You can access Fabric and Common code in this
        // project.

        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            Services.PLATFORM.sendToClient(new S2CSyncAbilityPacket(handler.player.getId(),Utils.getAbility(handler.player)),handler.player);
        });
        EntitySleepEvents.STOP_SLEEPING.register((entity, sleepingPos) -> {
            if (entity instanceof ServerPlayer serverPlayer) {
                NewAbility ability = Utils.getAbility(entity);
                if (ability != null) {
                ability.onWakeup(serverPlayer);
                }
            }
        });
        CommandRegistrationCallback.EVENT.register((commandDispatcher, commandBuildContext, commandSelection) -> ModCommands.register(commandDispatcher));
        EntityElytraEvents.CUSTOM.register((entity, tickElytra) -> Utils.hasFakeElytra(entity));
        ServerPlayerEvents.AFTER_RESPAWN.register(CustomAbilities::afterRespawn);
        ModParticleTypes.init();
        ModAttributes.init();
        ModMobEffects.init();
        CommonDataAttachments.init();
        ModEntityTypes.init();
        ModSoundEvents.init();
        ModBlocks.init();
        // Use Fabric to bootstrap the Common mod.
        CustomAbilities.init();
    }
}
