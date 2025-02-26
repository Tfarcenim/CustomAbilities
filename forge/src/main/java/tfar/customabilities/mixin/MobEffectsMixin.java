package tfar.customabilities.mixin;

import net.minecraft.world.effect.MobEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tfar.customabilities.init.ModMobEffects;

@Mixin(MobEffects.class)
public class MobEffectsMixin {
    @Inject(method = "<clinit>",at = @At("RETURN"))
    private static void onInit(CallbackInfo ci) {
        ModMobEffects.init();
    }
}
