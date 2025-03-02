package tfar.customabilities.ability;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;

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
    public int getNaturalProtectionPoints(LivingEntity livingEntity, DamageSource source) {
        return source.is(DamageTypes.FALL) ? 12 : super.getNaturalProtectionPoints(livingEntity, source);
    }
}
