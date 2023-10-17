package tfar.customabilities.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tfar.customabilities.Ability;
import tfar.customabilities.Constants;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Shadow public abstract boolean isInvisible();

    @Inject(method = "getMaxAirSupply",at = @At("HEAD"),cancellable = true)
    private void changeAir(CallbackInfoReturnable<Integer> cir) {
        if ((Object)this instanceof Player player && Constants.hasAbility(player, Ability.Otty)) {
            cir.setReturnValue(20 * 60 * 8);//8 minutes in ticks
        }
    }

    @Inject(method = "shouldRender",at = @At("HEAD"),cancellable = true)
    private void invisible(double $$0, double $$1, double $$2, CallbackInfoReturnable<Boolean> cir) {
        if ((Object)this instanceof Player player && Constants.hasAbility(player, Ability.Ramsey) && isInvisible()) {
            cir.setReturnValue(false);
        }
    }
}
