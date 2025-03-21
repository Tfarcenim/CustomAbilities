package tfar.customabilities.ability;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import tfar.customabilities.Utils;

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
        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED,5 * 20,2));
        player.addEffect(new MobEffectInstance(MobEffects.JUMP,5 * 20,2));
        Utils.setEscapeTimer(player,100);
        addCooldown(player,0,30 * 20);
    }

    @Override
    public boolean isImmuneTo(DamageSource source) {
        return source.is(DamageTypeTags.IS_FALL) || super.isImmuneTo(source);
    }

    @Override
    public void tick(ServerPlayer player) {
        super.tick(player);
        int escapeTimer = Utils.getEscapeTimer(player);
        if (escapeTimer > 0) {
            escapeTimer--;
            if (escapeTimer == 0) {
                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,15 * 20,2,false,false));
            }
            Utils.setEscapeTimer(player,escapeTimer);
        }
    }
}
