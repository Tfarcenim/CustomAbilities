package tfar.customabilities;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class Hooks {

    public static final ThreadLocal<Player> eatingPlayer = ThreadLocal.withInitial(() -> null);

    public static int getEatenHunger(int original, ItemStack stack) {
        Player player = eatingPlayer.get();
        if (Utils.hasAbility(player,Abilities.BARCODE)) {

        }
        return original;
    }
}
