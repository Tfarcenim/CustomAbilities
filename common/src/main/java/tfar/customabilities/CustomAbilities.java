package tfar.customabilities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.gameevent.GameEvent;
import tfar.customabilities.platform.Services;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Items;
import tfar.customabilities.world.deferredevent.DeferredEvent;
import tfar.customabilities.world.deferredevent.DeferredEventSystem;

// This class is part of the common project meaning it is shared between all supported loaders. Code written here can only
// import and access the vanilla codebase, libraries used by vanilla, and optionally third party libraries that provide
// common compatible binaries. This means common code can not directly use loader specific concepts such as Forge events
// however it will be compatible with all supported mod loaders.
public class CustomAbilities {

    public static final String MOD_ID = "customabilities";

    // The loader specific projects are able to import and use any code from the common project. This allows you to
    // write the majority of your code here and load it from your loader specific projects. This example has some
    // code that gets invoked by the entry point of the loader specific projects.
    public static void init() {
        Constants.LOG.info("Hello from Common init on {}! we are currently in a {} environment!", Services.PLATFORM.getPlatformName(), Services.PLATFORM.getEnvironmentName());
        Constants.LOG.info("The ID for diamonds is {}", BuiltInRegistries.ITEM.getKey(Items.DIAMOND));

        // It is common for all supported loaders to provide a similar feature that can not be used directly in the
        // common code. A popular way to get around this is using Java's built-in service loader feature to create
        // your own abstraction layer. You can learn more about this in our provided services class. In this example
        // we have an interface in the common code and use a loader specific implementation to delegate our call to
        // the platform specific approach.
    }

    public static DeferredEventSystem getDeferredEventSystem(ServerLevel serverLevel) {
        return serverLevel.getDataStorage()
                .computeIfAbsent(DeferredEventSystem::loadStatic,DeferredEventSystem::new,MOD_ID+ ":deferredevents");
    }

    public static void addDeferredEvent(ServerLevel level, DeferredEvent event) {
        getDeferredEventSystem(level).addDeferredEvent(event);
    }

    public static boolean onSculkEvent(ServerLevel pLevel, BlockPos pPos, GameEvent pGameEvent, GameEvent.Context pContext) {
        Entity entity = pContext.sourceEntity();
        if (entity instanceof Player player && Constants.hasAbility(player,Ability.Syd)) {
            return true;
        }
        return false;
    }

    public static void spawnServersideParticles(Player player) {
        if (player.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.SOUL,player.getRandomX(0.5D), player.getRandomY(), player.getRandomZ(0.5D),
                    1,0, 0, 0,0);
        }
    }
}

// LIST
//sprite heals by just being in the sunlight, should only be phsyical block/item light tources, flight boost cooldown fixed to be 30 seconds
//
//syd is effected by warden blindness
//
//new addition:
//Muw abilities:
//Guitar Strum:
//When holding the Lute item (from immersive melodies mod) and pressing left click while sneaking, Muw can strum the strings (plays a randomized noteblock sound) and cause the effects of a Lingering potion of healing around her. There should be a small cooldown.
//Evil guitar strum:
//
//When holding the Redstone Guitar item (from Music Maker mod) and pressing left click while sneaking, Muw can strum the strings (plays a randomized noteblock sound) and cause a shockwave that pushes all entities in 4x4 block radius away from her. There should be a small cooldown.
//
//(if there is no way to code that for those two specific items, maybe just make those abilities two hotkeys that muw can press at any time?)
//
//Other abilities:
//Muw always spawns in with her acoustic guitar (Lute item), even upon death