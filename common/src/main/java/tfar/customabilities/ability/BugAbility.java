package tfar.customabilities.ability;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.EntityHitResult;
import tfar.customabilities.Utils;
import tfar.customabilities.init.ModMobEffects;

/*
Bug
- Permanent Speed 1 (No particles)
        - Not affected by poison
- has 4 extra HP
- Drowns 50% faster
- Burns 50% slower
- Sinks to the floor when in water. Cannot swim back up when under 2 blocks of water
- Hunger never lowers

//"Fast" Keybind toggle - Grants Bug Speed 3 until toggled off.

//"Heal" Keybind - When activated with crosshair over a play, the targeted player receives 2 hp of damage followed by Regeneration (potency 3) for 15 seconds.

*/
public class BugAbility extends NewAbility{
    public BugAbility(String name) {
        super(name);
    }

    @Override
    public void handlePrimary(ServerPlayer player) {
        super.handlePrimary(player);
        if (player.hasEffect(MobEffects.MOVEMENT_SPEED)) {
            player.removeEffect(MobEffects.MOVEMENT_SPEED);
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED,MobEffectInstance.INFINITE_DURATION,0,false,false));
        } else {
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, MobEffectInstance.INFINITE_DURATION, 2, false, false));
        }
    }

    @Override
    public void handleSecondary(ServerPlayer player) {
        super.handleSecondary(player);
        EntityHitResult result = Utils.pickEntity(player,5,5,1);
        if (result != null) {
            Entity entity = result.getEntity();
            if (entity instanceof LivingEntity livingEntity) {
                livingEntity.hurt(player.damageSources().generic(),2);
                livingEntity.addEffect(new MobEffectInstance(MobEffects.REGENERATION,20 * 15,2,false,false));
            }
        }
    }
}
