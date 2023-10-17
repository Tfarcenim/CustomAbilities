package tfar.customabilities.mixin;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.extensions.IForgeItem;
import org.spongepowered.asm.mixin.Mixin;
import tfar.customabilities.Constants;

@Mixin(Item.class)
public abstract class ItemMixin implements IForgeItem {

    @Override
    public boolean canElytraFly(ItemStack stack, LivingEntity entity) {
        if (Constants.hasFakeElytra(entity)) return true;
        return IForgeItem.super.canElytraFly(stack, entity);
    }

    @Override
    public boolean elytraFlightTick(ItemStack stack, LivingEntity entity, int flightTicks) {
        if (Constants.hasFakeElytra(entity)) return true;
        return IForgeItem.super.elytraFlightTick(stack, entity, flightTicks);
    }
}
