package tfar.customabilities;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tfar.customabilities.ability.NewAbility;
import tfar.customabilities.network.PacketHandler;
import tfar.customabilities.platform.Services;

// This class is part of the common project meaning it is shared between all supported loaders. Code written here can only
// import and access the vanilla codebase, libraries used by vanilla, and optionally third party libraries that provide
// common compatible binaries. This means common code can not directly use loader specific concepts such as Forge events
// however it will be compatible with all supported mod loaders.
public class CustomAbilities {

    public static final String MOD_ID = "customabilities";
    public static final String MOD_NAME = "CustomAbilities";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

    // The loader specific projects are able to import and use any code from the common project. This allows you to
    // write the majority of your code here and load it from your loader specific projects. This example has some
    // code that gets invoked by the entry point of the loader specific projects.
    public static void init() {
        PacketHandler.registerPackets();
        // It is common for all supported loaders to provide a similar feature that can not be used directly in the
        // common code. A popular way to get around this is using Java's built-in service loader feature to create
        // your own abstraction layer. You can learn more about this in our provided services class. In this example
        // we have an interface in the common code and use a loader specific implementation to delegate our call to
        // the platform specific approach.
    }

    public static ResourceLocation id(String ability) {
        return new ResourceLocation(MOD_ID,ability);
    }

    public static void tick(ServerPlayer serverPlayer) {
        NewAbility ability = Services.PLATFORM.getAbility(serverPlayer);
        if (ability != null) {
            ability.tick(serverPlayer);
        }
        ScheduledCallback scheduledCallback = Services.PLATFORM.getScheduledCallback(serverPlayer);
        if (scheduledCallback != null) {
            if (scheduledCallback.timer > 0) {
                scheduledCallback.timer--;
                if (scheduledCallback.timer == 0) {
                    scheduledCallback.consumer.accept(serverPlayer);
                }
            }
        }
    }

    public static float onLivingHurt(LivingEntity target, DamageSource source,float amount) {
        Entity attacker = source.getEntity();
        if (Utils.hasAbility(target,Abilities.BARCODE)) {
            if (source.is(DamageTypeTags.IS_FIRE)) {
                amount *=1.35f;
            }
        }else if (Utils.hasAbility(target,Abilities.SYD)) {
            if (attacker instanceof LivingEntity livingAttacker) {
                if (livingAttacker.getRandom().nextDouble() < .15) {
                    livingAttacker.addEffect(new MobEffectInstance(MobEffects.POISON,3 * 20,0));
                }
            }
        }
        return amount;
    }

    public static void afterRespawn(ServerPlayer oldPlayer, ServerPlayer player, boolean alive) {
        if (!alive) {
            NewAbility ability = Services.PLATFORM.getAbility(player);
            if (ability != null) {
                ability.onRespawn(player);
            }
        }
    }

    public static boolean nativeAquaAffinity(Player player) {
        return false;
    }
}