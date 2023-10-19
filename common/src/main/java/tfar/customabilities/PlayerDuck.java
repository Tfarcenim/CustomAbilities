package tfar.customabilities;

import net.minecraft.nbt.CompoundTag;

import javax.annotation.Nullable;

public interface PlayerDuck {

    @Nullable Ability getAbility();
    void setAbility(@Nullable Ability ability);
    boolean constructed();
    CompoundTag getModData();
    void setModData(CompoundTag tag);
    int getFlightBoostCooldown();
    void setFlightBoostCooldown(int cooldown);

    int getTeleportCooldown();
    void setTeleportCooldown(int cooldown);

}
