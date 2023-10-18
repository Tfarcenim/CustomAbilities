package tfar.customabilities;

import javax.annotation.Nullable;

public interface PlayerDuck {

    @Nullable Ability getAbility();
    void setAbility(@Nullable Ability ability);
    boolean constructed();
    int getFlightBoostCooldown();
    void setFlightBoostCooldown(int cooldown);

    int getTeleportCooldown();
    void setTeleportCooldown(int cooldown);

}
