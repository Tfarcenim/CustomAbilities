package tfar.customabilities.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import tfar.customabilities.CustomAbilities;

@Mixin(Item.class)
public class ItemMixin {

    @Redirect(method = "use",at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;canEat(Z)Z"))
    private boolean isEdibleEvent(Player instance, boolean canAlwaysEat, @Local ItemStack stack) {
        return CustomAbilities.canPlayerEat(instance,canAlwaysEat,stack);
    }
}
