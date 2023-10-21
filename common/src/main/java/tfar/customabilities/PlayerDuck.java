package tfar.customabilities;

import net.minecraft.nbt.CompoundTag;

import javax.annotation.Nullable;

public interface PlayerDuck {

    @Nullable Ability getAbility();
    void setAbility(@Nullable Ability ability);

    CompoundTag getModData();
    void setModData(CompoundTag tag);
    default void setFlightBoostCooldown(int flightBoostCooldown) {
        this.getModData().putInt("flight_boost",flightBoostCooldown);
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

    default int getCrouchTime() {
        return getModData().getInt("crouchTime");
    }

    default void setCrouchTime(int crouchTime) {
        getModData().putInt("crouchTime",crouchTime);
    }

    default boolean garAbilityActive() {
        return getModData().getBoolean("gar_glowing");
    }

    default void setGarAbility(boolean active) {
        getModData().putBoolean("gar_glowing",active);
    }
}
