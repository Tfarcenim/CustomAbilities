package tfar.customabilities.ability;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import tfar.customabilities.Utils;

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
        Utils.flightBoost(player);
        addCooldown(player,0,20);
    }

    @Override
    public void handleSecondary(ServerPlayer player) {
        super.handleSecondary(player);
        if (player.hasEffect(MobEffects.SLOW_FALLING)) {
            player.removeEffect(MobEffects.SLOW_FALLING);
        } else {
            player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, MobEffectInstance.INFINITE_DURATION, 0, false, false));
        }
    }

    @Override
    public void onRemove(ServerPlayer player) {
        super.onRemove(player);
        player.removeEffect(MobEffects.SLOW_FALLING);
    }

    @Override
    public void onWakeup(ServerPlayer player) {
        super.onWakeup(player);
        player.addItem(new ItemStack(Items.EGG));
    }
}
