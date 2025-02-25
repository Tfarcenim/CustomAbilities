package tfar.customabilities.ability;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

/*
Jackalope
- Permanent speed 1 (+20% walking/running speed) (No particles)
        - Permanent jump boost 2 (+100% jump height) (No particles)
        - No fall damage
- Damage noise replaced with rabbit damage noise (if possible lol)

"Escape" Keybind - Grants Jackalope Speed 2 for 5 seconds (+40% walking/running speed,) Jump Boost 3 for 5 seconds (+150% jump height.)
After those effects wear off Jackalope gets Slowness 3 for 15 seconds after it wears off (-45% walking/running speed.)
This keybind has a 30 second cooldown. (No particles)

*/
public class JackalopeAbility extends NewAbility{

    public JackalopeAbility(String jackalope) {
        super(jackalope);
    }

    @Override
    public void handlePrimary(ServerPlayer player) {
        super.handlePrimary(player);
        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED,5 * 20,1));
        player.addEffect(new MobEffectInstance(MobEffects.JUMP,5 * 20,2));
        addCooldown(player,0,30 * 20);
    }
}
