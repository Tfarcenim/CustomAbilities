package tfar.customabilities.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tfar.customabilities.CustomAbilities;
import tfar.customabilities.Utils;
import tfar.customabilities.ability.NewAbility;

@Mixin(LivingEntity.class)
//@Debug(export = true)
public abstract class LivingEntityMixinFabric extends Entity {
    @ModifyArg(method = "actuallyHurt",at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;getDamageAfterArmorAbsorb(Lnet/minecraft/world/damagesource/DamageSource;F)F"))
    private float onLivingHurt(float f, @Local(argsOnly = true) DamageSource source) {
        return CustomAbilities.onLivingHurt((LivingEntity) (Object)this,source,f);
    }

    @ModifyVariable(method = "actuallyHurt", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;setAbsorptionAmount(F)V", shift = At.Shift.AFTER), ordinal = 0, argsOnly = true)
    private float onLivingDamage(float f,@Local(argsOnly = true) DamageSource source) {
        return CustomAbilities.onLivingDamaged((LivingEntity) (Object)this,source,f);
    }

    @Redirect(method = "addEatEffect",at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;addEffect(Lnet/minecraft/world/effect/MobEffectInstance;)Z"))
    private boolean onEatEffect(LivingEntity instance, MobEffectInstance effectInstance) {
        NewAbility ability = Utils.getAbility(this);
        return (ability == null || !ability.isImmuneToFoodEffect(effectInstance)) && instance.addEffect(effectInstance);
    }

    @Inject(method = "canBeAffected",at = @At("RETURN"),cancellable = true)
    private void shouldEffect(MobEffectInstance mobEffectInstance, CallbackInfoReturnable<Boolean> cir) {
        if (!cir.getReturnValue()) {
            return;
        }
        if (CustomAbilities.shouldPrevent((LivingEntity)(Object) this,mobEffectInstance)) {
            cir.setReturnValue(false);
        }
    }


    @ModifyVariable(method = "travel",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;onGround()Z",ordinal = 2))
    private float modifyFriction(float original) {
        return CustomAbilities.frictionEvent((LivingEntity)(Object)this,original);
    }



    public LivingEntityMixinFabric(EntityType<?> $$0, Level $$1) {
        super($$0, $$1);
    }
}

