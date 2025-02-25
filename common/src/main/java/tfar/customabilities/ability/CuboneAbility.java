package tfar.customabilities.ability;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

//Cubone  Slightly based on the DUCK origin by LunWri
//- Always has an invisible elytra equipped. Can still equip armor in the chest slot (Like Mari’s ability in our previous commission)
//- Permanent Slow Falling (No particles)
//- Takes 25 seconds to start drowning
//- Burns 50% faster
//- Has 16 health points
//- Can’t wear armor above iron (this is not necessary to code if you don’t want to lol…)
//- Omnivore
//- After sleeping in a bed, an egg is put in their inventory
//
//"Launch" Keybind - Will rocket boost Cubone. (Like Mari’s ability in our previous commission) (This keybind has a 1 second cooldown)
public class CuboneAbility extends NewAbility{


    public CuboneAbility(String cubone) {
        super(cubone);
    }

    @Override
    public void handlePrimary(ServerPlayer player) {
        super.handlePrimary(player);
        flightBoost(player);
        addCooldown(player,0,20);
    }


    public static void flightBoost(ServerPlayer player) {
        ItemStack firework = new ItemStack(Items.FIREWORK_ROCKET);
        FireworkRocketEntity fireworkRocketEntity = new FireworkRocketEntity(player.level(), firework, player);
        player.level().addFreshEntity(fireworkRocketEntity);
    }
}
