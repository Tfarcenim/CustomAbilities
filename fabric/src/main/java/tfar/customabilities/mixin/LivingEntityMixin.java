package tfar.customabilities.mixin;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.*;

import java.util.Map;

@Mixin(LivingEntity.class)
@Debug(export = true)
public abstract class LivingEntityMixin extends Entity {
    @Shadow
    @Final
    private Map<MobEffect, MobEffectInstance> activeEffects;

    public LivingEntityMixin(EntityType<?> $$0, Level $$1) {
        super($$0, $$1);
    }
}

