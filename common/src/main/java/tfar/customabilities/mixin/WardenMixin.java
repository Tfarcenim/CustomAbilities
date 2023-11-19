package tfar.customabilities.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tfar.customabilities.Ability;
import tfar.customabilities.Constants;

@Mixin(Warden.class)
public class WardenMixin {

    @Inject(method = "canTargetEntity",at = @At("RETURN"),cancellable = true)
    private void isFriendlyTo(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        boolean targetable = cir.getReturnValue();
        if (targetable && entity instanceof Player player && Constants.hasAbility(player, Ability.Syd)) {
            cir.setReturnValue(false);
        }
    }
}
