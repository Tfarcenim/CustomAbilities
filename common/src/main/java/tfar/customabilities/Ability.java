package tfar.customabilities;

import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;

import java.util.function.Consumer;

public enum Ability {
    Bug(),
//todo            -Levitation toggle
//todo-Emit light source toggle (player head becomes light source)

    Flo(
            Constants.combine(Constants.createPermanentEffect(MobEffects.WATER_BREATHING),
                    Constants.addAttributeModifier(Attributes.ATTACK_KNOCKBACK, Constants.modifier)),
            Constants.combine(Constants.removePermanentEffect(MobEffects.WATER_BREATHING),
                    Constants.removeAttributeModifier(Attributes.ATTACK_KNOCKBACK, Constants.modifier)),Constants.NOTHING),
//-Punches have higher knockback
//-Water breathing


    Mari(Constants.NOTHING, Constants.NOTHING, AbilityTickers::tickMari,false,false,true),
//-Permanent invisible elytra (chestplate compatible) with flight boost every 30 seconds
//todo-15 second speed boost ability. Cooldown of 5 minutes. If possible lightning effects when enabled
//todo-Ability to summon a lightning strike at a chosen location. Teleport to location when struck. Cooldown 10 minutes.

    Syd(Constants.createPermanentEffect(MobEffects.NIGHT_VISION), Constants.removePermanentEffect(MobEffects.NIGHT_VISION),Constants.NOTHING),
//-Night vision
//todo-Sculk sensors alert syd (spectral arrow effect)
//todo-Strength boost in darker light values
//-Warden will not attack

    Barcode,
    //todo          -Turn into bat

    Basil(Constants.createPermanentEffect(MobEffects.WATER_BREATHING), Constants.removePermanentEffect(MobEffects.WATER_BREATHING),Constants.NOTHING,true,false,false),
//-Water breathing
//-Swim and mine speed unaffected in water
//-Any horse can be ridden, even without saddle

    Gar(Constants.NOTHING, Constants.NOTHING,AbilityTickers::tickGar),
    //todo-Crouching for 10 seconds will toggle spectral arrow view on entities within a 5 block radius
    //-Speed boost when on less than 25% health
    //-Hunger decreases slower

    Miblex(Constants.NOTHING, Constants.NOTHING,AbilityTickers::tickMiblex,false,true,false),
//-Full moon triggers mining fatigue and slowness
//-New moon triggers strength, hunger, night vision and speed
//todo-teleportation ability
//-water contact deals damage

    Otty(Constants.NOTHING, player -> player.setAirSupply(300),Constants.NOTHING,true,false,false),
    //todo-water breathing 8 minutes
//-swim and mine speed unaffected in water
//-can see in water clearly
//-can sleep underwater already possible?
//-cannot sleep unless a player is sleeping within a 3 block radius

    Ramsey(Constants.NOTHING, player -> player.setInvisible(false),Constants.NOTHING),
//-Kills grant 1 extra heart (stackable) until hearts are lost via damage.
//todo-Kills also grant speed and damage boost for 1 minute (stackable). Soul particles when enabled.
//-invisibility toggle (no armor or particles shown)

    Spriteboba(Constants.createPermanentEffect(MobEffects.FIRE_RESISTANCE), Constants.removePermanentEffect(MobEffects.FIRE_RESISTANCE),AbilityTickers::tickSpriteBoba,false,true,true),
    //-Permanent invisible elytra (chestplate compatible) with flight boost every 2 minutes. Cooldown will only recharge when on ground.
    //-Immune to fire damage.
    //-Standing on light sources regenerates health.
    //-Water contact deals damage

    Saus;
    //todo    -Shapeshift into mobs (exclude ender dragon) and players
//todo-Can see dropped ores with a spectral arrow glow effect


    public final Consumer<Player> onAbilityAcquired;
    public final Consumer<Player> onAbilityRemoved;
    public final boolean nativeAquaAffinity;
    public final boolean hurtByWater;
    public final boolean fakeElytra;
    public final Consumer<Player> tickAbility;

    Ability() {
        this(Constants.NOTHING,Constants.NOTHING,Constants.NOTHING);
    }

    Ability(Consumer<Player> onAbilityAcquired, Consumer<Player> onAbilityRemoved,Consumer<Player> tickAbility) {
        this(onAbilityAcquired,onAbilityRemoved,tickAbility,false,false,false);

    }

    Ability(Consumer<Player> onAbilityAcquired, Consumer<Player> onAbilityRemoved,Consumer<Player>tickAbility,boolean nativeAquaAffinity,boolean hurtByWater,boolean fakeElytra) {
        this.onAbilityAcquired = onAbilityAcquired;
        this.onAbilityRemoved = onAbilityRemoved;
        this.tickAbility = tickAbility;
        this.nativeAquaAffinity = nativeAquaAffinity;
        this.hurtByWater = hurtByWater;
        this.fakeElytra = fakeElytra;
    }
}
