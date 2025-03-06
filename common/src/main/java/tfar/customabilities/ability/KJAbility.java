package tfar.customabilities.ability;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.phys.Vec3;

//Kj
//- Always has an invisible elytra equipped. Can still equip armor in the chest slot (Like Mari’s ability in our previous commission)
//- Permanent Feather Falling
//- 25% chance to deal poison damage when causing damage to another player/mob. This damage will lower the health of players, but cannot kill them (as in it cannot do the finishing blow) When a player is at half a heart, they will not take damage from this effect.
//- Has 4 extra HP
//
//"Fireball" Keybind - Shoots a ghast fireball. This keybind has a 60 second cooldown.
public class KJAbility extends NewAbility{
    public KJAbility(String name) {
        super(name);
    }

    @Override
    public void handlePrimary(ServerPlayer player) {
        super.handlePrimary(player);
        if (!player.isSilent()) {
            player.level().levelEvent(null, LevelEvent.SOUND_BLAZE_FIREBALL, player.blockPosition(), 0);
        }

        Vec3 look = player.getLookAngle();
        double d1 = look.x;
        double d2 = look.y;
        double d3 = look.z;
        double d4 = 0.5D;

        LargeFireball smallfireball = new LargeFireball(player.level(), player, d1,d2,d3,1);
        //smallfireball.setPos(smallfireball.getX(), player.getY(0.5D) + 0.5D, smallfireball.getZ());
        player.level().addFreshEntity(smallfireball);
        addCooldown(player,3,60*20);
    }

    @Override
    public int getNaturalProtectionPoints(LivingEntity livingEntity, DamageSource source) {
        return source.is(DamageTypes.FALL) ? 12 : super.getNaturalProtectionPoints(livingEntity, source);
    }
}
