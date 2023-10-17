package tfar.customabilities.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tfar.customabilities.Constants;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    public LivingEntityMixin(EntityType<?> $$0, Level $$1) {
        super($$0, $$1);
    }

    @Inject(method = "isSensitiveToWater",at = @At("HEAD"),cancellable = true)
    private void waterDamage(CallbackInfoReturnable<Boolean> cir) {
        if ((Object)this instanceof Player player && Constants.hurtByWater(player)) {
            cir.setReturnValue(true);
        }
    }
}
