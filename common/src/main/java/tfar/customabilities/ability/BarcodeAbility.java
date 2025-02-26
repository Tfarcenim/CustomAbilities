package tfar.customabilities.ability;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import tfar.customabilities.CustomAbilities;
import tfar.customabilities.Utils;

//Barcode
//- Permanent Slow Falling
//- Raw meat (beef, pork , mutton, pork-chop, rotten flesh, etc.) restores 5-6 hunger bars. Immune to food poisoning
//- Burns 35% faster
//- Moving from light level 1-6 to 15 in under 2 seconds will cause Blindness for 5 seconds
public class BarcodeAbility extends NewAbility {
    public BarcodeAbility(String barcode) {
        super(barcode);
    }

    @Override
    public void tick(ServerPlayer player) {
        super.tick(player);

        int lightLevel = player.serverLevel().getMaxLocalRawBrightness(player.blockPosition());
        CustomAbilities.insertLightLevel(player,lightLevel);

        int[] lightLevels = Utils.getPreviousLightLevels(player);
        if (lightLevel == 15) {
            boolean shouldBlind = false;
            for (int i = 1; i < lightLevels.length;i++) {
                int prevLightLevel = lightLevels[i];
                if ( prevLightLevel < 7) {
                    shouldBlind = true;
                    break;
                }
            }
            if (shouldBlind) {
                player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS,20 * 5));
            }
        }
    }
}
