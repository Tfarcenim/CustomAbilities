package tfar.customabilities.mixin;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tfar.customabilities.Ability;
import tfar.customabilities.PlayerDuck;

import javax.annotation.Nullable;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity implements PlayerDuck {
    @SuppressWarnings("all") //shush
    private static final EntityDataAccessor<Integer> ABILITY = SynchedEntityData.defineId(Player.class, EntityDataSerializers.INT);

    protected PlayerMixin(EntityType<? extends LivingEntity> $$0, Level $$1) {
        super($$0, $$1);
    }

    @Inject(method = "defineSynchedData",at = @At("RETURN"))
    private void registerCustom(CallbackInfo ci) {
        this.entityData.define(ABILITY, -1);
        constructed = true;
    }


    @Inject(method = "addAdditionalSaveData",at = @At("RETURN"))
    private void addExtra(CompoundTag tag, CallbackInfo ci) {
        Ability ability = getAbility();
        if (ability != null) {
            tag.putInt("ability", getAbility().ordinal());
        }
    }

    @Inject(method = "readAdditionalSaveData",at = @At("RETURN"))
    private void readExtra(CompoundTag tag, CallbackInfo ci) {
        if (tag.contains("ability")) {
            setAbility(Ability.values()[tag.getInt("ability")]);
        }
    }

    @Override
    @Nullable
    public Ability getAbility() {
        if (!constructed) return null;
        int index = entityData.get(ABILITY);
        return (index >= 0 && index < Ability.values().length) ? Ability.values()[index] : null;
    }

    @Override
    public void setAbility(@Nullable Ability ability) {
        entityData.set(ABILITY,ability == null ? -1:ability.ordinal());
    }

    private boolean constructed;

    @Override
    public boolean constructed() {
        return constructed;
    }
}
