package tfar.customabilities;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import tfar.customabilities.attachments.CommonDataAttachments;

public class CustomAbilitiesFabric implements ModInitializer {
    
    @Override
    public void onInitialize() {
        
        // This method is invoked by the Fabric mod loader when it is ready
        // to load your mod. You can access Fabric and Common code in this
        // project.


        CommandRegistrationCallback.EVENT.register((commandDispatcher, commandBuildContext, commandSelection) -> ModCommands.register(commandDispatcher));
        ServerPlayerEvents.AFTER_RESPAWN.register(CustomAbilities::afterRespawn);
        ModParticleTypes.init();
        CommonDataAttachments.init();
        // Use Fabric to bootstrap the Common mod.
        CustomAbilities.init();
    }
}
