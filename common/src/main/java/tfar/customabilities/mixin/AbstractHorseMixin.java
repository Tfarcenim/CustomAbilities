package tfar.customabilities.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tfar.customabilities.Ability;
import tfar.customabilities.Constants;
import tfar.customabilities.PlayerDuck;

@Mixin(AbstractHorse.class)
public abstract class AbstractHorseMixin extends Entity {
    public AbstractHorseMixin(EntityType<?> $$0, Level $$1) {
        super($$0, $$1);
    }

    @Redirect(method = "onPlayerJump",at = @At(value = "INVOKE",target = "Lnet/minecraft/world/entity/animal/horse/AbstractHorse;isSaddled()Z"))
    private boolean foolSaddle(AbstractHorse instance) {
        if (isBasil()) {
            return true;
        }
        return instance.isSaddled();
    }

    @Inject(method = "canJump",at = @At("HEAD"),cancellable = true)
    private void foolSaddle2(CallbackInfoReturnable<Boolean> cir) {
        if (isBasil()) {
            cir.setReturnValue(true);
        }
    }

    @Redirect(method = "getControllingPassenger",at = @At(value = "INVOKE",target = "Lnet/minecraft/world/entity/animal/horse/AbstractHorse;isSaddled()Z"))
    private boolean foolSaddle3(AbstractHorse instance) {
        if (isBasil()) {
            return true;
        }
        return instance.isSaddled();
    }
    private boolean isBasil() {
        Entity living = getFirstPassenger();
        return living instanceof Player player && Constants.hasAbility(player,Ability.Basil);
    }
}
