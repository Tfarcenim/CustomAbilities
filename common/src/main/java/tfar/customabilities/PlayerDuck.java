package tfar.customabilities;

import com.mojang.datafixers.kinds.Const;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;
import java.util.List;

public interface PlayerDuck {



    CompoundTag getModData();
    default void setModData(CompoundTag tag) {
        setModData(tag,false);
    }
    void setModData(CompoundTag tag,boolean forceSync);
    default void setFlightBoostCooldown(int flightBoostCooldown) {
        this.getModData().putInt("flight_boost",flightBoostCooldown);
    }

    default void setKeptItems(NonNullList<ItemStack> items) {
        CompoundTag keptTag = new CompoundTag();
        ContainerHelper.saveAllItems(keptTag,items);
        getModData().put("kept_items",keptTag);
    }

    default NonNullList<ItemStack> getKeptItems() {
        NonNullList<ItemStack> stacks = NonNullList.create();
        CompoundTag tag = getModData().getCompound("kept_items");
        Constants.loadAllItems(tag,stacks);
        return stacks;
    }

    default void clearKept() {

    }

    default int getFlightBoostCooldown() {
        return getModData().getInt("flight_boost");
    }

    default int getTeleportCooldown() {
        return getModData().getInt("teleport");
    }

    default void setTeleportCooldown(int cooldown) {
        this.getModData().putInt("teleport",cooldown);
    }

    default int getSpeedBoostCooldown() {
        return getModData().getInt("speed_boost");
    }

    default void setSpeedBoostCooldown(int cooldown) {
        getModData().putInt("speed_boost",cooldown);
    }

    @Nullable Ability getAbility();
    void setAbility(@Nullable Ability ability);

    default int getCrouchTime() {
        return getModData().getInt("crouchTime");
    }

    default void setCrouchTime(int crouchTime) {
        getModData().putInt("crouchTime",crouchTime);
    }

    default boolean garAbilityActive() {
        return getModData().getBoolean("gar_glowing");
    }

    default boolean ramseyParticles() {
        return getModData().getBoolean("ramsey_particles");
    }

    default void setRamseyParticles(boolean active) {
        CompoundTag tag = getModData();
        tag.putBoolean("ramsey_particles",active);
        setModData(tag,true);
    }

    default void setGarAbility(boolean active) {
        CompoundTag tag = getModData();
        tag.putBoolean("gar_glowing",active);
        setModData(tag,true);
    }
}
