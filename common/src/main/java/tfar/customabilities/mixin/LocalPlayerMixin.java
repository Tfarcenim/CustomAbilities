package tfar.customabilities.mixin;

import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tfar.customabilities.Ability;
import tfar.customabilities.PlayerDuck;

@Mixin(LocalPlayer.class)
public class LocalPlayerMixin {

    @Inject(method = "getWaterVision",at = @At("HEAD"),cancellable = true)
    private void clearView(CallbackInfoReturnable<Float> cir) {
        if (((PlayerDuck)this).getAbility() == Ability.Otty) {
            cir.setReturnValue(1f);
        }
    }
}
