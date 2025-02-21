package tfar.customabilities.ability;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

//Barcode
//- Permanent Slow Falling
//- Raw meat (beef, pork , mutton, pork-chop, rotten flesh, etc.) restores 5-6 hunger bars. Immune to food poisoning
//- Burns 35% faster
//- Moving from light level 1-6 to 15 in under 2 seconds will cause Blindness for 5 seconds
public class BarcodeAbility extends NewAbility {
    @Override
    public void onGive(ServerPlayer player) {
        super.onGive(player);
        player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING,-1,0,false,false));
    }

    @Override
    public void onRemove(ServerPlayer player) {
        super.onRemove(player);
        player.removeEffect(MobEffects.SLOW_FALLING);
    }
}
