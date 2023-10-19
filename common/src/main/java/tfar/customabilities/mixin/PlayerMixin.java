package tfar.customabilities.mixin;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemCooldowns;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tfar.customabilities.Ability;
import tfar.customabilities.Constants;
import tfar.customabilities.PlayerDuck;

import javax.annotation.Nullable;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity implements PlayerDuck {

    @SuppressWarnings("all") //shush
    private static final EntityDataAccessor<CompoundTag> MOD_DATA = SynchedEntityData.defineId(Player.class,EntityDataSerializers.COMPOUND_TAG);

    @SuppressWarnings("all") //shush
    private static final EntityDataAccessor<Integer> ABILITY = SynchedEntityData.defineId(Player.class, EntityDataSerializers.INT);
    
    protected PlayerMixin(EntityType<? extends LivingEntity> $$0, Level $$1) {
        super($$0, $$1);
    }

    @Inject(method = "defineSynchedData",at = @At("RETURN"))
    private void registerCustom(CallbackInfo ci) {
        this.entityData.define(ABILITY, -1);
        this.entityData.define(MOD_DATA,new CompoundTag());
        constructed = true;
    }

    @Override
    public CompoundTag getModData() {
        return entityData.get(MOD_DATA);
    }

    @Override
    public void setModData(CompoundTag tag) {
        entityData.set(MOD_DATA,tag);
    }

    @Override
    public void setFlightBoostCooldown(int flightBoostCooldown) {
        this.getModData().putInt("flight_boost",flightBoostCooldown);
    }

    @Override
    public int getFlightBoostCooldown() {
        return getModData().getInt("flight_boost");
    }

    @Override
    public int getTeleportCooldown() {
        return getModData().getInt("teleport");
    }

    @Override
    public void setTeleportCooldown(int cooldown) {
        this.getModData().putInt("teleport",cooldown);
    }

    @Inject(method = "addAdditionalSaveData",at = @At("RETURN"))
    private void addExtra(CompoundTag tag, CallbackInfo ci) {
        Ability ability = getAbility();
        if (ability != null) {
            tag.putInt("ability", getAbility().ordinal());
        }
        tag.put("mod_data",getModData());
    }

    @Inject(method = "readAdditionalSaveData",at = @At("RETURN"))
    private void readExtra(CompoundTag tag, CallbackInfo ci) {
        if (tag.contains("ability")) {
            setAbility(Ability.values()[tag.getInt("ability")]);
        }
        setModData(tag.getCompound("mod_data"));
    }

    @Inject(method = "tryToStartFallFlying",at = @At("RETURN"), cancellable = true)
    private void hijackFlightCheck(CallbackInfoReturnable<Boolean> cir) {
        boolean alreadyTrue = cir.getReturnValue();
        if (alreadyTrue) return;
        if (Constants.fakeElytra((Player) (Object)this)) {
            cir.setReturnValue(true);
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
