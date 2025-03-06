package tfar.customabilities.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tfar.customabilities.Abilities;
import tfar.customabilities.Hooks;
import tfar.customabilities.Utils;
//@Debug(export = true)
@Mixin(FoodData.class)
public class FoodDataMixin {
   /* @ModifyConstant(method = "tick",constant = @Constant(floatValue = 4))//increasing this value makes hunger tick slower
    private float modify(float value, Player player) {
        if (Constants.hasAbility(player, Ability.Gar)) {
            return value * 1.5f;
        }
        return value;
    }*/


    @Shadow private float exhaustionLevel;

    @ModifyArg(method = "eat(Lnet/minecraft/world/item/Item;Lnet/minecraft/world/item/ItemStack;)V",at = @At(value = "INVOKE", target = "Lnet/minecraft/world/food/FoodData;eat(IF)V"))
    private int getEatenHunger(int original, @Local(argsOnly = true) ItemStack stack) {
        return Hooks.getEatenHunger(original,stack);
    }

    @Inject(method = "tick",at = @At(value = "FIELD", target = "Lnet/minecraft/world/food/FoodData;exhaustionLevel:F",ordinal = 0))
    private void modify(Player player, CallbackInfo ci) {
        if (Utils.hasAbility(player, Abilities.BUG)) {
            exhaustionLevel = 0;
        }
    }
}
