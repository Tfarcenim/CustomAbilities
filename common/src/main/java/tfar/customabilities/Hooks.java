package tfar.customabilities;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class Hooks {

    public static final ThreadLocal<Player> eatingPlayer = ThreadLocal.withInitial(() -> null);

    ////- Sweet foods restore more hunger points, can eat sugar. (Cookie: 5 points, Cake slice: 8 points, Full cake: 24 points,
    //// Honey: 6 points, Sweet Berry: 4 points, Sugar: 4 points)

    static final Object2IntMap<Item> SWEET_FOODS = new Object2IntOpenHashMap<>();

    static {
        SWEET_FOODS.put(Items.COOKIE,5);
        SWEET_FOODS.put(Items.HONEY_BOTTLE,6);
        SWEET_FOODS.put(Items.SWEET_BERRIES,4);
    }

    public static int getEatenHunger(int original, ItemStack stack) {
        Player player = eatingPlayer.get();
        if (Utils.hasAbility(player,Abilities.BARCODE)) {
            if (stack.is(ModTags.RAW_MEATS)) {
                return 6;
            }
        } else if (Utils.hasAbility(player,Abilities.STABBERZ) && SWEET_FOODS.containsKey(stack.getItem())) {
            return SWEET_FOODS.getInt(stack.getItem());
        }
        return original;
    }
}
