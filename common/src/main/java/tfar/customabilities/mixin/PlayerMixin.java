package tfar.customabilities.mixin;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity {
    @Inject(method = "canEat",at = @At("RETURN"),cancellable = true)
    private void isEdibleEvent(boolean canAlwaysEat, CallbackInfoReturnable<Boolean> cir) {

    }

    protected PlayerMixin(EntityType<? extends LivingEntity> $$0, Level $$1) {
        super($$0, $$1);
    }
}

