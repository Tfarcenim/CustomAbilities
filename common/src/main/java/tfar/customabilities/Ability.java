package tfar.customabilities;

import net.minecraft.world.entity.player.Player;

import java.util.function.Consumer;

public enum Ability {
    Bug(Constants.NOTHING,Constants.NOTHING),
//            -Levitation toggle
//-Emit light source toggle (player head becomes light source)

    Flo(Constants.NOTHING,Constants.NOTHING),
  //          -Water breathing
//-Punches have higher knockback

    Mari(Constants.NOTHING,Constants.NOTHING),
//            -Permanent invisible elytra (chestplate compatible) with flight boost every 30 seconds
//-15 second speed boost ability. Cooldown of 5 minutes. If possible lightning effects when enabled
//-Ability to summon a lightning strike at a chosen location. Teleport to location when struck. Cooldown 10 minutes.

            Syd(Constants.NOTHING,Constants.NOTHING),
//-Night vision
//-Sculk sensors alert syd (spectral arrow effect)
//-Strength boost in darker light values

    Barcode(Constants.NOTHING,Constants.NOTHING),
  //          -Turn into bat

    Basil(Constants.NOTHING,Constants.NOTHING),
//            -Water breathing
//-Swim and mine speed unaffected in water
//-Any horse can be ridden, even without saddle

    Gar(Constants.NOTHING,Constants.NOTHING),
  //          -Crouching for 10 seconds will toggle spectral arrow view on entities within a 5 block radius
//-Speed boost when on less than 25% health
//-Hunger decreases slower

    Miblex(Constants.NOTHING,Constants.NOTHING),
//            -Full moon triggers mining fatigue and slowness
//-New moon triggers strength, hunger, night vision and speed
//-teleportation ability
//-water contact deals damage

    Otty(Constants.NOTHING,Constants.NOTHING),
  //          -water breathing 8 minutes
//-swim and mine speed unaffected in water
//-can see in water clearly
//-can sleep underwater
//-cannot sleep unless a player is sleeping within a 3 block radius

    Ramsey(Constants.NOTHING,Constants.NOTHING),
//            -Kills grant 1 extra heart (stackable) until hearts are lost via damage.
//            -Kills also grant speed and damage boost for 1 minute (stackable). Soul particles when enabled.
//            -invisibility toggle (no armor or particles shown)

    Spriteboba(Constants.NOTHING,Constants.NOTHING),
     //       -Permanent invisible elytra (chestplate compatible) with flight boost every 2 minutes. Cooldown will only recharge when on ground.
//-Immune to fire damage.
  //          -Standing on light sources regenerates health.
    //        -Water contact deals damage

    Saus(Constants.NOTHING,Constants.NOTHING);
    //    -Shapeshift into mobs (exclude ender dragon) and players
//-Can see dropped ores with a spectral arrow glow effect

    //finished
    static {
        //            -Warden will not attack

    }

    public final Consumer<Player> onAbilityAcquired;
    public final Consumer<Player> onAbilityRemoved;

    Ability(Consumer<Player> onAbilityAcquired,Consumer<Player> onAbilityRemoved) {
        this.onAbilityAcquired = onAbilityAcquired;
        this.onAbilityRemoved = onAbilityRemoved;
    }
}
