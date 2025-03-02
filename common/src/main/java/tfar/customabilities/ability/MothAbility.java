package tfar.customabilities.ability;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.EntityHitResult;
import tfar.customabilities.Utils;
import tfar.customabilities.init.ModMobEffects;

//Moth
//- Always has an invisible elytra equipped. Can still equip armor in the chest slot (Like Mari’s ability in our previous commission)
//- Does not take fall damage
//- Takes 25% more burning damage
//
//"Launch" Keybind - Will rocket boost Pepper. (Like Mari’s ability in our previous commission) (This keybind has a 1 second cooldown)
//
//        "Lies" Keybind - When activated with crosshair over a player, the targeted player receives blindness (No particles) for 2 seconds.
//        Moth receives red potion particles for 4 seconds.
//
//"Nightvision" Keybind toggle - Toggling grants Night vision until toggled off. (No particles)
public class MothAbility extends NewAbility{
    public MothAbility(String name) {
        super(name);
    }

    @Override
    public void handlePrimary(ServerPlayer player) {
        super.handlePrimary(player);
        if (player.isFallFlying()) {
            Utils.flightBoost(player);
            addCooldown(player,0,20);
        }
    }

    @Override
    public void handleSecondary(ServerPlayer player) {
        super.handleSecondary(player);
        EntityHitResult result = Utils.pickEntity(player,5,5,1);
        if (result != null) {
            Entity entity = result.getEntity();
            if (entity instanceof LivingEntity livingEntity) {
                livingEntity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS,40,0,false,false));
                player.addEffect(new MobEffectInstance(ModMobEffects.RED,80));
            }
        }
    }

    @Override
    public void handleTertiary(ServerPlayer player) {
        super.handleTertiary(player);
        if (player.hasEffect(MobEffects.NIGHT_VISION)) {
            player.removeEffect(MobEffects.NIGHT_VISION);
        } else {
            player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, MobEffectInstance.INFINITE_DURATION, 0, false, false));
        }
    }

    @Override
    public void onRemove(ServerPlayer player) {
        super.onRemove(player);
        player.removeEffect(MobEffects.NIGHT_VISION);
    }

    @Override
    public boolean isImmuneTo(DamageSource source) {
        return super.isImmuneTo(source) && source.is(DamageTypeTags.IS_FALL);
    }
}
