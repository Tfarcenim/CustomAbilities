package tfar.customabilities.mixin;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
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

    @Inject(method = "getDepthStrider",at = @At("RETURN"),cancellable = true)
    private static void waterAbility2(LivingEntity living, CallbackInfoReturnable<Integer> cir) {
            int nativeDepthStrider = CustomAbilities.getDepthStriderAbility(living);
            int enchantmentDepthStrider = cir.getReturnValue();
            if (nativeDepthStrider > enchantmentDepthStrider) {
                cir.setReturnValue(nativeDepthStrider);
            }
    }
}
