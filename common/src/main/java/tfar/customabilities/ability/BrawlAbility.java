package tfar.customabilities.ability;

import net.minecraft.server.level.ServerPlayer;

//Brawl
//- Can eat raw meat without being affected by hunger (raw chicken, rotten flesh.) Can only eat meat, cannot eat bread, vegetables, berries, etc.
//        - Takes 75% less fall damage
//- Permanent Regeneration (Potency 1) (No particles)
//
//All toggles except Smoke Screen include explosion sound effect and particles
//
//"Explode" Keybind - Will immediately set off an explosion equal to 1 TNT block wherever Brawl is standing. Brawl does not get knocked back from the explosion and receives 18 points of damage. (Other players/mobs will receive the normal 39 hp damage TNT does) This keybind has a cooldown of 90 seconds
//
//"Bomb toss" Keybind toggle - Launches a bomb 5 blocks away that destroys a random amount of blocks within a 2x2x2 radius. Does 7 points of damage to players/mobs in that radius. This keybind has a cooldown of 5 seconds
//
//        (If possible within budget) "Sticky bomb" Keybind toggle - Can stick a bomb to a player/mob that is within 3 blocks. (Crosshair has to be overtop of the player/mob to apply it) Explodes 10 seconds after toggling and will explode in the current location of the player/mob it was stuck to. destroys a random amount of blocks within a 2x2x2 radius. Does 7 points of damage to players/mobs in that radius. This keybind has a cooldown of 10 seconds.
//
//"Smoke Screen" Keybind toggle - Toggling this ability will place down campfire smoke in a 5x5x3 radius for 30 seconds. This keybind has a cooldown of 120 seconds.
//
//"Bomb Boost" Keybind toggle - Toggling this ability will launch Brawl 8 blocks in the direction that he is looking. Deals 7 hp worth of damage to Brawl.
public class BrawlAbility extends NewAbility{
    public BrawlAbility(String name) {
        super(name);
    }

    @Override
    public void handlePrimary(ServerPlayer player) {
        super.handlePrimary(player);

    }
}
