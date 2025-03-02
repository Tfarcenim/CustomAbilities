package tfar.customabilities.ability;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import tfar.customabilities.Abilities;
import tfar.customabilities.Utils;
import tfar.customabilities.init.ModMobEffects;

//Mari
//- Every half a second, Mari passively has a 5% chance to deal half a heart of damage to any player within a five block radius.
// Damage dealt will play a small electric buzz noise. A menu to adjust the chance percentage to any value can be accessible through a keybind.
// This damage will lower the health of players, but cannot kill them (as in it cannot do the finishing blow, similar to the effects of poison.)
// When a player is at half a heart, they will not take damage from this effect. This effect does not affect Mari.
//        - Lightning that strikes within a 100 block radius will strike directly on top of Mari instead. Mari is immune to this damage.
//
//"Charge" Keybind - This ability causes the next punch with an empty hand to deal 8 health points of damage to the player/mob, regardless of armor. (Only one punch until the keybind is activated again.) This keybind has a cooldown of 30 seconds.
//
//"Teleportation" Keybind - Teleports Mari 12 blocks in the direction the player is looking. Mari cannot teleport through solid blocks to prevent them from suffocating.
//
//"Fast" Keybind - Grants Mari Speed 1 for 15 seconds. This keybind has a cooldown of 15 seconds. (No particles)
public class MariAbility extends NewAbility {

    public MariAbility(String mari) {
        super(mari);
    }

    @Override
    public void tick(ServerPlayer player) {
        super.tick(player);
        if (player.tickCount % 10 ==0 && player.getRandom().nextDouble() < .05) {
            Player nearby = player.level().getNearestPlayer(player.getX(), player.getY(), player.getZ(), 5, (entity) -> {
                return entity != null && entity.isAlive() && entity instanceof LivingEntity livingEntity && livingEntity.getHealth() >1;
            });
            if (nearby!=null) {
                nearby.hurt(player.damageSources().lightningBolt(),1);
            }
        }
    }

    @Override
    public void handlePrimary(ServerPlayer player) {
        super.handlePrimary(player);
        player.addEffect(new MobEffectInstance(ModMobEffects.ELECTRO_FIST,MobEffectInstance.INFINITE_DURATION,0,false,false));
        addCooldown(player,2,30*20);
    }

    @Override
    public void handleSecondary(ServerPlayer player) {
        super.handleSecondary(player);
        Utils.teleportPlayerToFacing(player);
    }

    @Override
    public void handleTertiary(ServerPlayer player) {
        super.handleTertiary(player);
        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED,20 * 30,0,false,false));
    }

    @Override
    public boolean isImmuneTo(DamageSource source) {
        return super.isImmuneTo(source) && source.is(DamageTypeTags.IS_LIGHTNING);
    }
}
