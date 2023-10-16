package tfar.customabilities;

import net.minecraft.world.entity.player.Player;

import java.util.function.Consumer;

public enum KeyAction {
    TOGGLE_INVISIBILITY(Ability.Ramsey, CustomAbilitiesForge::toggleTrueInvis);

    private final Ability required;
    private final Consumer<Player> playerConsumer;

    KeyAction(Ability required, Consumer<Player> playerConsumer) {
        this.required = required;
        this.playerConsumer = playerConsumer;
    }

    public boolean canUse(Player player) {
        return Constants.hasAbility(player,required);
    }

    public void performAction(Player player) {
        playerConsumer.accept(player);
    }



}
