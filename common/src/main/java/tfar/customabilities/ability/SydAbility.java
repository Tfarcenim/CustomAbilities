package tfar.customabilities.ability;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;

import java.util.List;

//        Syd
//- Permanent water breathing (No particles)
//- 30% speed boost when underwater (No particles)
//- When under water, night vision is granted (No particles)
//- Hitting Syd has a 15% chance to Poison (potency 1) the player/mob that hit Syd for 3 seconds.
//
//"Toxin" Keybind - This keybind grants Withering (potency 2) players/mobs within a 7x7x2 radius for 7 seconds.
// Will grant Syd Regeneration (potency 2) and Nausea for 5 seconds. This keybind has a 40 second cooldown. (No particles)
public class SydAbility extends NewAbility{

    @Override
    public void tick(ServerPlayer player) {
        super.tick(player);
    }

    @Override
    public void handlePrimary(ServerPlayer player) {
        super.handlePrimary(player);
        ServerLevel level = player.serverLevel();
        AABB aabb = new AABB(player.position(),player.position()).inflate(3.5,3.5,1);
        List<Entity> entities = level.getEntities(player, aabb, entity -> entity.isAlive() && entity.isAttackable());
        for (Entity entity : entities) {
            if (entity instanceof LivingEntity livingEntity) {
                livingEntity.addEffect(new MobEffectInstance(MobEffects.WITHER,7 * 20,1));
            }
        }

        player.addEffect(new MobEffectInstance(MobEffects.REGENERATION,5 *20,10));
        player.addEffect(new MobEffectInstance(MobEffects.CONFUSION,5 *20,10));

        addCooldown(player,0,40 * 20);
    }
}
