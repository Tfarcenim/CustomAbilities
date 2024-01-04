package tfar.customabilities.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.AnvilBlock;
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


    @Override
    public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, Player player) {
        if(BuiltInRegistries.ITEM.getKey(itemstack.getItem()).equals(Constants.LUTE_RL) && player.isCrouching()) {
            return true;
        }
        return IForgeItem.super.onBlockStartBreak(itemstack, pos, player);
    }

}
