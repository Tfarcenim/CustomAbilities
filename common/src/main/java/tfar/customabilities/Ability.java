package tfar.customabilities;

import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import tfar.customabilities.platform.Services;

import java.util.function.Consumer;

public enum Ability {
    Bug(),
//-Levitation toggle
//-Emit light source toggle (player head becomes light source)

    Flo(
            Constants.combine(Constants.createPermanentEffect(MobEffects.WATER_BREATHING),
                    Constants.addAttributeModifier(Attributes.ATTACK_KNOCKBACK, Constants.modifier)),true,
            Constants.combine(Constants.removePermanentEffect(MobEffects.WATER_BREATHING),
                    Constants.removeAttributeModifier(Attributes.ATTACK_KNOCKBACK, Constants.modifier)),Constants.NOTHING),
//-Punches have higher knockback
//-Water breathing


    Mari(Constants.NOTHING, true,Constants.NOTHING, AbilityTickers::tickMari,false,false,true),
//-Permanent invisible elytra (chestplate compatible) with flight boost every 30 seconds
//-15 second speed boost ability. Cooldown of 5 minutes. If possible lightning effects when enabled
//-Ability to summon a lightning strike at a chosen location. Teleport to location when struck. Cooldown 10 minutes.

    Syd(Constants.createPermanentEffect(MobEffects.NIGHT_VISION), true,Constants.removePermanentEffect(MobEffects.NIGHT_VISION),AbilityTickers::tickSyd),
//-Night vision
//-Sculk sensors alert syd (spectral arrow effect)
//-Strength boost in darker light values
//-Warden will not attack

    Barcode(Constants.NOTHING,true,Constants.NOTHING,Constants.NOTHING),
    //-Turn into bat

    Basil(Constants.createPermanentEffect(MobEffects.WATER_BREATHING),true, Constants.removePermanentEffect(MobEffects.WATER_BREATHING),Constants.NOTHING,true,false,false),
//-Water breathing
//-Swim and mine speed unaffected in water
//-Any horse can be ridden, even without saddle

    Gar(Constants.NOTHING,true ,Constants.NOTHING,AbilityTickers::tickGar),
    //-Crouching for 10 seconds will toggle spectral arrow view on entities within a 5 block radius
    //-Speed boost when on less than 25% health
    //-Hunger decreases slower

    Miblex(Constants.NOTHING,true, Constants.NOTHING,AbilityTickers::tickMiblex,false,true,false),
//-Full moon triggers mining fatigue and slowness
//-New moon triggers strength, hunger, night vision and speed
//-teleportation ability
//-water contact deals damage

    Otty(player -> player.setAirSupply(Constants.OTTY_AIR),true, player -> player.setAirSupply(300),Constants.NOTHING,true,false,false),
    //todo-water breathing 8 minutes
//-swim and mine speed unaffected in water
//-can see in water clearly
//-can sleep underwater already possible?
//-cannot sleep unless a player is sleeping within a 3 block radius

    Ramsey(Constants.NOTHING,true, player -> player.setInvisible(false),Constants.NOTHING),
//-Kills grant 1 extra heart (stackable) until hearts are lost via damage.
//-Kills also grant speed and damage boost for 1 minute (stackable). Soul particles when enabled.
//-invisibility toggle (no armor or particles shown)

    Spriteboba(Constants.createPermanentEffect(MobEffects.FIRE_RESISTANCE), true,Constants.removePermanentEffect(MobEffects.FIRE_RESISTANCE),AbilityTickers::tickSpriteBoba,false,true,true),
    //-Permanent invisible elytra (chestplate compatible) with flight boost every 2 minutes. Cooldown will only recharge when on ground.
    //-Immune to fire damage.
    //-Standing on light sources regenerates health.
    //-Water contact deals damage


    Muw(player -> Constants.addItemToInv(player),false,Constants.NOTHING,Constants.NOTHING),

    Saus(Services.PLATFORM::addAllIdentities,false, Services.PLATFORM::removeAllIdentities,Constants.NOTHING);
    //todo    -Shapeshift into mobs (exclude ender dragon) and players
//-Can see dropped ores with a spectral arrow glow effect


    public final Consumer<Player> onAbilityAcquired;
    public final boolean shouldReApplyOnDeath;
    public final Consumer<Player> onAbilityRemoved;
    public final boolean nativeAquaAffinity;
    public final boolean hurtByWater;
    public final boolean fakeElytra;
    public final Consumer<Player> tickAbility;

    Ability() {
        this(Constants.NOTHING,true,Constants.NOTHING,Constants.NOTHING);
    }

    Ability(Consumer<Player> onAbilityAcquired,boolean shouldReApplyOnDeath, Consumer<Player> onAbilityRemoved,Consumer<Player> tickAbility) {
        this(onAbilityAcquired,shouldReApplyOnDeath,onAbilityRemoved,tickAbility,false,false,false);

    }

    Ability(Consumer<Player> onAbilityAcquired,boolean shouldReApplyOnDeath, Consumer<Player> onAbilityRemoved,Consumer<Player>tickAbility,boolean nativeAquaAffinity,boolean hurtByWater,boolean fakeElytra) {
        this.onAbilityAcquired = onAbilityAcquired;
        this.shouldReApplyOnDeath = shouldReApplyOnDeath;
        this.onAbilityRemoved = onAbilityRemoved;
        this.tickAbility = tickAbility;
        this.nativeAquaAffinity = nativeAquaAffinity;
        this.hurtByWater = hurtByWater;
        this.fakeElytra = fakeElytra;
    }
}
