package tfar.customabilities;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;

import java.util.function.Consumer;

public enum Ability {
    Bug(Constants.NOTHING, Constants.NOTHING),
//todo            -Levitation toggle
//todo-Emit light source toggle (player head becomes light source)

    Flo(
            Constants.combine(Constants.createPermanentEffect(MobEffects.WATER_BREATHING),
                    Constants.addAttributeModifier(Attributes.ATTACK_KNOCKBACK, Constants.modifier)),
            Constants.combine(Constants.removePermanentEffect(MobEffects.WATER_BREATHING),
                    Constants.removeAttributeModifier(Attributes.ATTACK_KNOCKBACK, Constants.modifier))),
//-Punches have higher knockback
//-Water breathing


    Mari(Constants.NOTHING, Constants.NOTHING),
//todo            -Permanent invisible elytra (chestplate compatible) with flight boost every 30 seconds
//todo-15 second speed boost ability. Cooldown of 5 minutes. If possible lightning effects when enabled
//todo-Ability to summon a lightning strike at a chosen location. Teleport to location when struck. Cooldown 10 minutes.

    Syd(Constants.createPermanentEffect(MobEffects.NIGHT_VISION), Constants.removePermanentEffect(MobEffects.NIGHT_VISION)),
//-Night vision
//todo-Sculk sensors alert syd (spectral arrow effect)
//todo-Strength boost in darker light values
//-Warden will not attack


    Barcode(Constants.NOTHING, Constants.NOTHING),
    //todo          -Turn into bat

    Basil(Constants.createPermanentEffect(MobEffects.WATER_BREATHING), Constants.removePermanentEffect(MobEffects.WATER_BREATHING),true),
//-Water breathing
//-Swim and mine speed unaffected in water
//-Any horse can be ridden, even without saddle

    Gar(Constants.NOTHING, Constants.NOTHING),
    //todo-Crouching for 10 seconds will toggle spectral arrow view on entities within a 5 block radius
    //-Speed boost when on less than 25% health
    //-Hunger decreases slower

    Miblex(Constants.NOTHING, Constants.NOTHING),
//-Full moon triggers mining fatigue and slowness
//-New moon triggers strength, hunger, night vision and speed
//todo-teleportation ability
//-water contact deals damage

    Otty(Constants.NOTHING, player -> player.setAirSupply(300),true),
    //todo-water breathing 8 minutes
//-swim and mine speed unaffected in water
//-can see in water clearly
//-can sleep underwater already possible?
//-cannot sleep unless a player is sleeping within a 3 block radius

    Ramsey(Constants.NOTHING, Constants.NOTHING),
//todo            -Kills grant 1 extra heart (stackable) until hearts are lost via damage.
//todo            -Kills also grant speed and damage boost for 1 minute (stackable). Soul particles when enabled.
//todo            -invisibility toggle (no armor or particles shown)

    Spriteboba(Constants.NOTHING, Constants.NOTHING),
    //todo       -Permanent invisible elytra (chestplate compatible) with flight boost every 2 minutes. Cooldown will only recharge when on ground.
//todo-Immune to fire damage.
    //todo          -Standing on light sources regenerates health.
    //todo        -Water contact deals damage

    Saus(Constants.NOTHING, Constants.NOTHING);
    //todo    -Shapeshift into mobs (exclude ender dragon) and players
//todo-Can see dropped ores with a spectral arrow glow effect


    public final Consumer<Player> onAbilityAcquired;
    public final Consumer<Player> onAbilityRemoved;
    public final boolean nativeAquaAffinity;

    Ability(Consumer<Player> onAbilityAcquired, Consumer<Player> onAbilityRemoved) {
        this(onAbilityAcquired,onAbilityRemoved,false);
    }

    Ability(Consumer<Player> onAbilityAcquired, Consumer<Player> onAbilityRemoved,boolean nativeAquaAffinity) {
        this.onAbilityAcquired = onAbilityAcquired;
        this.onAbilityRemoved = onAbilityRemoved;
        this.nativeAquaAffinity = nativeAquaAffinity;
    }
}
