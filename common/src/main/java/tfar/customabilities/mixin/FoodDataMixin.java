package tfar.customabilities.mixin;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import tfar.customabilities.Ability;
import tfar.customabilities.Constants;

@Mixin(FoodData.class)
public class FoodDataMixin {
    @ModifyConstant(method = "tick",constant = @Constant(floatValue = 4))//increasing this value makes hunger tick slower
    private float modify(float value, Player player) {
        if (Constants.hasAbility(player, Ability.Gar)) {
            return value * 1.5f;
        }
        return value;
    }
}
