package tfar.customabilities.mixin;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tfar.customabilities.Ability;
import tfar.customabilities.Constants;

import java.util.Map;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    @Shadow @Final private Map<MobEffect, MobEffectInstance> activeEffects;

    public LivingEntityMixin(EntityType<?> $$0, Level $$1) {
        super($$0, $$1);
    }

    @Inject(method = "isSensitiveToWater",at = @At("HEAD"),cancellable = true)
    private void waterDamage(CallbackInfoReturnable<Boolean> cir) {
        if ((Object)this instanceof Player player && Constants.hurtByWater(player)) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "tickEffects",at = @At(value = "INVOKE",target = "Lnet/minecraft/network/syncher/SynchedEntityData;get(Lnet/minecraft/network/syncher/EntityDataAccessor;)Ljava/lang/Object;",ordinal = 1))
    private void addSoul(CallbackInfo ci) {
        if ( (Object)this instanceof Player player && activeEffects.keySet().contains(MobEffects.MOVEMENT_SPEED) && Constants.hasAbility(player,Ability.Ramsey)) {

                //double d0 = (double)(i >> 16 & 255) / 255.0D;
                //double d1 = (double)(i >> 8 & 255) / 255.0D;
                //double d2 = (double)(i >> 0 & 255) / 255.0D;
                this.level().addParticle(ParticleTypes.SOUL, this.getRandomX(0.5D), this.getRandomY(), this.getRandomZ(0.5D),0,0,0);
            }
    }
}
