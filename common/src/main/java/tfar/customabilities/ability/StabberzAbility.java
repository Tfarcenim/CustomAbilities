package tfar.customabilities.ability;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import tfar.customabilities.ModParticleTypes;
import tfar.customabilities.Utils;

import java.util.List;

//Stabberz
//- Sweet foods restore more hunger points, can eat sugar. (Cookie: 5 points, Cake slice: 8 points, Full cake: 24 points,
// Honey: 6 points, Sweet Berry: 4 points, Sugar: 4 points)
//
//        "Float" Keybind toggle - Toggle to float (like Bug’s power in our previous commission.)
//
//"Glow" Keybind toggle - Toggle to emit light level 13 from the player. (Reference: Holding a torch with optifine or shaders.)
// (like Bug’s power in our previous commission.)
//
//        "Bubbles" Keybind - Shoots bubble particles 5 blocks out from Stabberz position (follows Stabberz position when moving.)
//        Knocks back players/mobs to outside of those 5 blocks. Grants Stabberz Hunger effect for 30 seconds after they toggle it.
//        (REF Supplementaries & Fossils and Archeology 's Bubble Blower)
public class StabberzAbility extends NewAbility {

    public StabberzAbility(String stabberz) {
        super(stabberz);
    }

    @Override
    public void handlePrimary(ServerPlayer player) {
        super.handlePrimary(player);
        if (player.hasEffect(MobEffects.LEVITATION)) {
            player.removeEffect(MobEffects.LEVITATION);
        } else {
            player.addEffect(new MobEffectInstance(MobEffects.LEVITATION, MobEffectInstance.INFINITE_DURATION, 0, false, false));
        }
    }

    @Override
    public void handleSecondary(ServerPlayer player) {
        super.handleSecondary(player);
        int emission = Utils.getLightLevel(player);
        if (emission > 0) {
            Utils.setLightLevel(player,0);
        } else {
            Utils.setLightLevel(player,13);
        }
    }

    @Override
    public void handleTertiary(ServerPlayer player) {
        super.handleTertiary(player);
        ServerLevel level = player.serverLevel();
        int x = 30;
        for (int i = 0;i < x;i++) {
            double radians = 2 *i* Math.PI / x;
            level.sendParticles(ModParticleTypes.BUBBLE, player.getX(), player.getY() + 1, player.getZ(), 0,
                    Mth.cos((float) radians), 0, Mth.sin((float) radians), 2);
        }

        AABB aabb = new AABB(player.position(),player.position()).inflate(20,10,10);
        List<Entity> entities = level.getEntities(player, aabb, entity -> entity.isAlive() && entity.isAttackable());
        for (Entity entity : entities) {
            Vec3 add = entity.position().subtract(player.position()).normalize().scale(2);
            entity.setDeltaMovement(entity.getDeltaMovement().add(add));
            entity.hurtMarked = true;
        }
        player.addEffect(new MobEffectInstance(MobEffects.HUNGER,20 * 30));
    }

    @Override
    public void onRemove(ServerPlayer player) {
        super.onRemove(player);
        player.removeEffect(MobEffects.SLOW_FALLING);
    }
}
