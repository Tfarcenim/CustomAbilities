package tfar.customabilities.ability;

import net.minecraft.server.level.ServerPlayer;
//        Ramsey
//
//"Invisibility" Keybind toggle - Toggling this ability will cause Ramsey’s player model to fade in/out of visibility
// (Same Ramsey invisibility as last commission, but with a fade if possible) (No particles)
public class RamseyAbility extends NewAbility{
    public RamseyAbility(String name) {
        super(name);
    }

    @Override
    public void handlePrimary(ServerPlayer player) {
        super.handlePrimary(player);
        player.setInvisible(!player.isInvisible());
    }

    @Override
    public void onRemove(ServerPlayer player) {
        super.onRemove(player);
        player.setInvisible(false);
    }
}
