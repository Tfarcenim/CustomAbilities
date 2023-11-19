package tfar.customabilities.world.deferredevent;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import tfar.customabilities.Ability;
import tfar.customabilities.Constants;
import tfar.customabilities.PlayerDuck;

import java.util.UUID;

public class AddMobEffects extends DeferredEvent {

    private UUID uuid;

    public AddMobEffects() {
        super(DeferredEventTypes.SCALE_LATER);
    }
    public AddMobEffects(long timer, ServerPlayer player) {
        this();
        this.timer = timer;
        this.uuid = player.getUUID();
    }

    @Override
    public boolean attemptRun(ServerLevel level) {
        ServerPlayer player = level.getServer().getPlayerList().getPlayer(uuid);
        if (player != null) {
            Ability ability = ((PlayerDuck)player).getAbility();
            if (ability != null && ability.shouldReApplyOnDeath) {
                ability.onAbilityAcquired.accept(player);
            }
            return true;
        } else {
            Constants.LOG.warn("Player was null?");
            return false;
        }
    }

    @Override
    public void writeWithoutMetaData(CompoundTag tag) {
        tag.putUUID("player",uuid);
    }

    @Override
    public void loadAdditional(CompoundTag tag) {
        uuid = tag.getUUID("player");
    }
}
