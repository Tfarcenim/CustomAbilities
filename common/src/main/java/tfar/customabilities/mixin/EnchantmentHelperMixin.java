package tfar.customabilities.mixin;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tfar.customabilities.CustomAbilities;

@Mixin(EnchantmentHelper.class)
public class EnchantmentHelperMixin {

    @Inject(method = "hasAquaAffinity",at = @At("HEAD"),cancellable = true)
    private static void waterAbility(LivingEntity living, CallbackInfoReturnable<Boolean> cir) {
        if (CustomAbilities.nativeAquaAffinity(living)) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "getEnchantmentLevel(Lnet/minecraft/world/item/enchantment/Enchantment;Lnet/minecraft/world/entity/LivingEntity;)I",at = @At("RETURN"),cancellable = true)
    private static void calcLevel(Enchantment enchantment, LivingEntity entity, CallbackInfoReturnable<Integer> cir) {
        int base = cir.getReturnValue();
        int builtinLevel = CustomAbilities.getBuiltInLevel(enchantment,entity);
        if (builtinLevel > base) {
            cir.setReturnValue(builtinLevel);
        }
    }
}
