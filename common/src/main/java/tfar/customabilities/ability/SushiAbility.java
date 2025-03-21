package tfar.customabilities.ability;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;

//Sushi
//- Permanent Depth Strider (+33% swim speed)
//- Permanent Water Breathing (No particles)
//- When under water, night vision is granted (No particles)
//- Permanent Aqua Affinity (No particles)
//- All hits deal an additional 3 points of damage
//- Burns 50% faster
//- Can eat raw meat without being affected by hunger (raw chicken, rotten flesh.) Can only eat meat, cannot eat bread, vegetables, berries, etc.
public class SushiAbility extends NewAbility{
    public SushiAbility(String name) {
        super(name);
    }

    @Override
    public float getNightVisionModifier(Player player, float original) {
        return player.isUnderWater() ? 1 : original;
    }

    @Override
    public boolean canEat(ItemStack stack) {
        return stack.getItem().getFoodProperties().isMeat();
    }

    @Override
    public int getNaturalEnchantmentLevel(LivingEntity entity, Enchantment enchantment) {
        return enchantment == Enchantments.DEPTH_STRIDER ? 2 : super.getNaturalEnchantmentLevel(entity, enchantment);
    }
}
