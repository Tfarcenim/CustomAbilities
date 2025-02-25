package tfar.customabilities.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import tfar.customabilities.CustomAbilities;

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

    public LivingEntityMixinFabric(EntityType<?> $$0, Level $$1) {
        super($$0, $$1);
    }
}

