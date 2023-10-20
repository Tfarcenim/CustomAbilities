package tfar.customabilities;

import net.minecraft.world.entity.player.Player;

import java.util.Set;
import java.util.function.Consumer;

public enum KeyAction {
    TOGGLE_INVISIBILITY(Ability.Ramsey, CustomAbilitiesForge::toggleTrueInvis),
    TELEPORT(Set.of(Ability.Mari,Ability.Miblex),Constants::teleportMari),
    FLIGHT_BOOST(Set.of(Ability.Mari,Ability.Spriteboba),CustomAbilitiesForge::flightBoost),
    TOGGLE_LEVITATION(Ability.Bug,Constants::toggleLevitation),
    BAT_MORPH(Ability.Barcode,CustomAbilitiesForge::toggleBatForm)
    ;

    private final Set<Ability> required;
    private final Consumer<Player> onActivate;

    KeyAction(Ability required, Consumer<Player> onActivate) {
        this(Set.of(required),onActivate);
    }

    KeyAction(Set<Ability> required, Consumer<Player> onActivate) {
        this.required = required;
        this.onActivate = onActivate;
    }

    public boolean canUse(Player player) {
        Ability ability = ((PlayerDuck)player).getAbility();
        return ability != null && required.contains(ability);
    }

    public void performAction(Player player) {
        onActivate.accept(player);
    }



}
