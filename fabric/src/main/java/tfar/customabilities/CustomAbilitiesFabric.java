package tfar.customabilities;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;

public class CustomAbilitiesFabric implements ModInitializer {
    
    @Override
    public void onInitialize() {
        
        // This method is invoked by the Fabric mod loader when it is ready
        // to load your mod. You can access Fabric and Common code in this
        // project.

        ServerPlayerEvents.AFTER_RESPAWN.register(CustomAbilities::afterRespawn);
        ModParticleTypes.init();

        // Use Fabric to bootstrap the Common mod.
        CustomAbilities.init();
    }
}
