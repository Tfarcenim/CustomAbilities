package tfar.customabilities.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tfar.customabilities.Ability;
import tfar.customabilities.Constants;

@Mixin(Entity.class)
public class EntityMixin {

    @Inject(method = "getMaxAirSupply",at = @At("HEAD"),cancellable = true)
    private void changeAir(CallbackInfoReturnable<Integer> cir) {
        if ((Object)this instanceof Player player && Constants.hasAbility(player, Ability.Otty)) {
            cir.setReturnValue(20 * 60 * 8);//8 minutes in ticks
        }
    }
}
