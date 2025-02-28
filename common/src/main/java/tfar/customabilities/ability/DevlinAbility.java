package tfar.customabilities.ability;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

//Devlin
//- Always has an invisible elytra equipped. Can still equip armor in the chest slot (Like Mari’s ability in our previous commission)
//- Sinks to the floor when in water. When under 2 blocks of water, Devlin has Slowness 1.
//        - Immune to damage caused by lightning strikes. Not immune for 30 seconds after being struck.
//
//        “Hover” Keybind toggle - Toggling makes all blocks around the player act like ice, plus a 10% speed increase.
//        The ice effect follows the player and only affects the player who used it. (if possible, the player will hover over water.)
//        Devlin does not take fall damage when hover is active.
//This ability has 5 uses before having to sleep to reset the count. If toggled when Devlin has run out of uses: Devlin takes 2 HP worth of damage and one random block within 3 blocks of Devlin will break.
//
//        “Boost” Keybind toggle - Toggling this ability will launch Devlin in the direction that he is looking. When walking it launches him 8 blocks. When flying, it rocket boosts Devlin.
//This ability has 5 uses before having to sleep to reset the count. If toggled when Devlin has run out of uses: Devlin takes 2 HP worth of damage and one random block within 3 blocks of Devlin will break.
public class DevlinAbility extends NewAbility{
    public DevlinAbility(String name) {
        super(name);
    }

    @Override
    public void tick(ServerPlayer player) {
        super.tick(player);
        if (player.isUnderWater()) {
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,5,0,false,false));
        }
    }

    @Override
    public void handleSecondary(ServerPlayer player) {
        super.handleSecondary(player);
    }
}
