package tfar.customabilities;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;

public class AbilityTickers {

    public static void tickMiblex(Player player) {
        if (!player.level().isDay() && player.level().getGameTime() % 20 == 0) {
            //-New moon triggers strength, hunger, night vision and speed
            if (player.level().getMoonBrightness() == 0) {
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 40, 0, true, true));
                player.addEffect(new MobEffectInstance(MobEffects.HUNGER, 40, 0, true, true));
                player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 600, 0, true, true));
                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 40, 0, true, true));
                //-Full moon triggers mining fatigue and slowness
            } else if (player.level().getMoonBrightness() == 1) {
                player.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 40, 0, true, true));
                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 0, true, true));
            }
        }
    }

    public static void tickGar(Player player) {
        if (lessThan25PercentHealth(player)) {
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 40, 0, true, true));
        }

        PlayerDuck playerDuck = (PlayerDuck) player;
        if (player.isCrouching()) {
            int crouchTime = playerDuck.getCrouchTime();
            playerDuck.setCrouchTime(crouchTime + 1);
            System.out.println(crouchTime);
            if (crouchTime > 200) {
                playerDuck.setGarAbility(!playerDuck.garAbilityActive());
                playerDuck.setCrouchTime(0);
            }
        }
    }

    public static void tickSpriteBoba(Player player) {
        BlockState feetBlockState = player.getBlockStateOn();
        if (feetBlockState.getLightEmission() > 0) {
            player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 40, 0, true, true));
        }
        if (player.onGround()) {
            tickFlightBoostCooldown((PlayerDuck) player);
        }
    }

    public static void tickMari(Player player) {
        PlayerDuck playerDuck = (PlayerDuck) player;

        if (playerDuck.getTeleportCooldown() > 0) {
            playerDuck.setTeleportCooldown(playerDuck.getTeleportCooldown()-1);
        }
        tickFlightBoostCooldown(playerDuck);
        if (playerDuck.getSpeedBoostCooldown() > 0) {
            playerDuck.setSpeedBoostCooldown(playerDuck.getSpeedBoostCooldown()-1);
        }
    }

    public static void tickFlightBoostCooldown(PlayerDuck playerDuck) {
        int cooldown = playerDuck.getFlightBoostCooldown();
        if (cooldown > 0) {
            playerDuck.setFlightBoostCooldown(cooldown - 1);
        }
    }

    private static boolean lessThan25PercentHealth(LivingEntity player) {
        return player.getHealth() / player.getMaxHealth() < .25;
    }
}
