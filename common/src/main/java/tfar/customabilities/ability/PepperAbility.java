package tfar.customabilities.ability;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import tfar.customabilities.Utils;

//Pepper
//- Takes 15% more damage from everything except falling.
//        - Permanent Feather Falling
//- Permanent Swift Sneak (Potency 3)
//- Permanent +15% walking/running speed
//
//“Invisibility” Keybind toggle - Toggling this ability will cause Pepper’s player model to fade in/out of visibility
// (Same Ramsey invisibility as last commission, but with a fade if possible) (No particles)
//
//        “Detect” Keybind toggle - Players within a 32 block radius from pepper have the glowing effect until toggled off. Only visible to Pepper
//
//"Elytra" Keybind toggle - Toggling equips/unequips invisible elytra. Can still equip armor in the chest slot
//
//"Launch" Keybind - Will rocket boost Pepper. (Like Mari’s ability in our previous commission) (This keybind has a 1 second cooldown)
public class PepperAbility extends NewAbility{
    public PepperAbility(String name) {
        super(name);
    }

    @Override
    public float modifyDamageTaken(LivingEntity target, DamageSource source, float amount) {
        float v = super.modifyDamageTaken(target, source, amount);
        return v * (source.is(DamageTypes.FALL) ? 1 : 1.15f);
    }

    @Override
    public void handlePrimary(ServerPlayer player) {
        super.handlePrimary(player);
        boolean wasActive = Utils.getPepperVision(player);
        Utils.setPepperVision(player,!wasActive);
    }

    @Override
    public void handleSecondary(ServerPlayer player) {
        super.handleSecondary(player);
        if (player.isFallFlying()) {
            Utils.flightBoost(player);
            addCooldown(player,0,20);
        }
    }

    @Override
    public int getNaturalEnchantmentLevel(LivingEntity entity, Enchantment enchantment) {
        if (enchantment == Enchantments.SWIFT_SNEAK) {
            return 3;
        }
        return super.getNaturalEnchantmentLevel(entity, enchantment);
    }

    @Override
    public void onRemove(ServerPlayer player) {
        super.onRemove(player);
        Utils.setPepperVision(player,false);
    }

    @Override
    public boolean shouldGlow(Player player, Entity lookingAt) {
        return player.distanceToSqr(lookingAt) < 1024 && Utils.getPepperVision(player);
    }

    @Override
    public int getNaturalProtectionPoints(LivingEntity livingEntity, DamageSource source) {
        return source.is(DamageTypes.FALL) ? 12 : super.getNaturalProtectionPoints(livingEntity, source);
    }
}
