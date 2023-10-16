package tfar.customabilities.mixin;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tfar.customabilities.Constants;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Inject(method = "isSensitiveToWater",at = @At("HEAD"),cancellable = true)
    private void waterDamage(CallbackInfoReturnable<Boolean> cir) {
        if ((Object)this instanceof Player player && Constants.hurtByWater(player)) {
            cir.setReturnValue(true);
        }
    }
}
