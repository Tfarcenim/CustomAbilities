package tfar.customabilities.mixin;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tfar.customabilities.Abilities;
import tfar.customabilities.Utils;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity {

    protected PlayerMixin(EntityType<? extends LivingEntity> $$0, Level $$1) {
        super($$0, $$1);
    }

    @Inject(method = "getHurtSound",at = @At("HEAD"),cancellable = true)
    private void modifyHurt(DamageSource damageSource, CallbackInfoReturnable<SoundEvent> cir) {
        if (Utils.hasAbility(this, Abilities.JACKALOPE)) {
            cir.setReturnValue(SoundEvents.RABBIT_HURT);
        }
    }
}

