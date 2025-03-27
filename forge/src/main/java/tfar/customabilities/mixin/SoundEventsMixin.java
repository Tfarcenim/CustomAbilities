package tfar.customabilities.mixin;

import net.minecraft.sounds.SoundEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tfar.customabilities.init.ModSoundEvents;

@Mixin(SoundEvents.class)
public class SoundEventsMixin {
    @Inject(method = "<clinit>",at = @At("RETURN"))
    private static void onInit(CallbackInfo ci) {
        ModSoundEvents.init();
    }
}
