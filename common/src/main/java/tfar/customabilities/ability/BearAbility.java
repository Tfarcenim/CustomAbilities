package tfar.customabilities.ability;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.phys.Vec3;
import tfar.customabilities.init.ModMobEffects;

//Bear
//- Permanent Strength 2 (No particles)
//        - Permanent Resistance 2 (No particles)
//        - Has 25 health points
//- Has Night vision during the night (No particles)
//- Takes 50% less damage from burning, freezing, and damage caused by lightning
//- Takes 50% longer to drown
//
//"Frosted Fingers" Keybind - Will make Bear’s punches apply Slowness 2 and Weakness 2 for 10 seconds.
// Repeated hits will not stack the countdown on the effects, but reset them. This keybind has a cooldown of 60 seconds.
//
//"Sushi eyes" Keybind - Will grant bear Night vision for 60 seconds. This keybind has a cooldown of 15 seconds
//
//"Electro Fist" Keybind- This ability causes the next punch with an empty hand to deal 5 health points of damage to the player/mob, regardless of armor.
// (Only one punch until the keybind is activated again.) This keybind has a cooldown of 90 seconds.
//
//"Blaze Fingers" Keybind - This ability throws three consecutive blaze fireballs. (Hue shifted to match soul fire, if possible.) This keybind has a cooldown of 60 seconds.
//
//"Smoke Screen" Keybind - This ability will place down campfire smoke in a 3x3x3 radius for 30 seconds. This keybind has a cooldown of 180 seconds.
public class BearAbility extends NewAbility{
    public BearAbility(String name) {
        super(name);
    }

    @Override
    public void handlePrimary(ServerPlayer player) {
        super.handlePrimary(player);
        player.addEffect(new MobEffectInstance(ModMobEffects.FROSTED_FINGERS,10*20,0,false,false));
        addCooldown(player,0,(60 + 10) * 20);
    }

    @Override
    public void handleSecondary(ServerPlayer player) {
        super.handleSecondary(player);
        player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION,60*20,0,false,false));
        addCooldown(player,1,75*20);
    }

    @Override
    public void handleTertiary(ServerPlayer player) {
        super.handleTertiary(player);
        player.addEffect(new MobEffectInstance(ModMobEffects.ELECTRO_FIST,MobEffectInstance.INFINITE_DURATION,0,false,false));
        addCooldown(player,2,75*20);
    }

    @Override
    public void handleQuaternary(ServerPlayer player) {
        super.handleQuaternary(player);
        if (!player.isSilent()) {
            player.level().levelEvent(null, LevelEvent.SOUND_BLAZE_FIREBALL, player.blockPosition(), 0);
        }

        Vec3 look = player.getLookAngle();
        double d1 = look.x;
        double d2 = look.y;
        double d3 = look.z;
        double d4 = 0.5D;


        for(int i = 0; i < 1; ++i) {
            SmallFireball smallfireball = new SmallFireball(player.level(), player, player.getRandom().triangle(d1, 2.297D * d4), d2, player.getRandom().triangle(d3, 2.297D * d4));
            smallfireball.setPos(smallfireball.getX(), player.getY(0.5D) + 0.5D, smallfireball.getZ());
            player.level().addFreshEntity(smallfireball);
        }
        addCooldown(player,3,60*20);
    }

    @Override
    public void handleQuinary(ServerPlayer player) {
        super.handleQuinary(player);
    }
}
