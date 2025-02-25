package tfar.customabilities.ability;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import tfar.customabilities.Utils;
import tfar.customabilities.platform.Services;

//Stabberz
//- Sweet foods restore more hunger points, can eat sugar. (Cookie: 5 points, Cake slice: 8 points, Full cake: 24 points,
// Honey: 6 points, Sweet Berry: 4 points, Sugar: 4 points)
//
//        "Float" Keybind toggle - Toggle to float (like Bug’s power in our previous commission.)
//
//"Glow" Keybind toggle - Toggle to emit light level 13 from the player. (Reference: Holding a torch with optifine or shaders.)
// (like Bug’s power in our previous commission.)
//
//        "Bubbles" Keybind - Shoots bubble particles 5 blocks out from Stabberz position (follows Stabberz position when moving.)
//        Knocks back players/mobs to outside of those 5 blocks. Grants Stabberz Hunger effect for 30 seconds after they toggle it.
//        (REF Supplementaries & Fossils and Archeology 's Bubble Blower)
public class StabberzAbility extends NewAbility {

    @Override
    public void handlePrimary(ServerPlayer player) {
        super.handlePrimary(player);
        if (player.hasEffect(MobEffects.LEVITATION)) {
            player.removeEffect(MobEffects.LEVITATION);
        } else {
            player.addEffect(new MobEffectInstance(MobEffects.LEVITATION, MobEffectInstance.INFINITE_DURATION, 0, false, false));
        }
    }

    @Override
    public void handleSecondary(ServerPlayer player) {
        super.handleSecondary(player);
        int emission = Utils.getLightLevel(player);
        if (emission > 0) {
            Utils.setLightLevel(player,0);
        } else {
            Utils.setLightLevel(player,13);
        }
    }
}
