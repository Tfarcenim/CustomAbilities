package tfar.customabilities.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tfar.customabilities.CustomAbilities;
import tfar.customabilities.init.ModAttributes;

@Mixin(LivingEntity.class)
//@Debug(export = true)
public class LivingEntityMixin {

    @Inject(method = "createLivingAttributes",at = @At("RETURN"))
    private static void append(CallbackInfoReturnable<AttributeSupplier.Builder> cir) {
        cir.getReturnValue().add(ModAttributes.FIRE_WEAKNESS).add(ModAttributes.DROWNING_WEAKNESS);
    }

    @Inject(method = "hurt",at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;isSleeping()Z"),cancellable = true)
    private void onAttacked(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (CustomAbilities.livingAttack((LivingEntity)(Object)this,source,amount)) {
            cir.setReturnValue(false);
        }
    }

    @WrapOperation(method = "getDamageAfterMagicAbsorb",at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/enchantment/EnchantmentHelper;getDamageProtection(Ljava/lang/Iterable;Lnet/minecraft/world/damagesource/DamageSource;)I"))
    private int modifyProtection(Iterable<ItemStack> stacks, DamageSource source, Operation<Integer> original) {
        int base = original.call(stacks,source);
        base = CustomAbilities.modifyProtection((LivingEntity)(Object)this,source,base);
        return base;
    }

}
