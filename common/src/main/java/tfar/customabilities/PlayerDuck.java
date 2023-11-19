package tfar.customabilities;

import net.minecraft.nbt.CompoundTag;

import javax.annotation.Nullable;

public interface PlayerDuck {



    CompoundTag getModData();
    default void setModData(CompoundTag tag) {
        setModData(tag,false);
    }
    void setModData(CompoundTag tag,boolean forceSync);
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
