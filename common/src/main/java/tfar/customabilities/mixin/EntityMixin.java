package tfar.customabilities.mixin;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tfar.customabilities.Ability;
import tfar.customabilities.EntityDuck;

@Mixin(Entity.class)
public abstract class EntityMixin implements EntityDuck {


    @Shadow @Final protected SynchedEntityData entityData;
    @SuppressWarnings("all") //shush
    private static final EntityDataAccessor<Boolean> SID_GLOW = SynchedEntityData.defineId(Entity.class, EntityDataSerializers.BOOLEAN);

    @Inject(method = "<init>",at = @At("RETURN"))
    private void registerCustom(CallbackInfo ci) {
        this.entityData.define(SID_GLOW,false);
    }

    @Override
    public boolean getSidGlow() {
        return entityData.get(SID_GLOW);
    }

    @Override
    public void setGlowForSid(boolean tag) {
        entityData.set(SID_GLOW,tag);
    }


    @Inject(method = "saveWithoutId",at = @At("RETURN"))
    private void addExtra(CompoundTag tag, CallbackInfoReturnable<CompoundTag> ci) {
        tag.putBoolean("sid_glow", getSidGlow());
    }

    @Inject(method = "load",at = @At("RETURN"))
    private void readExtra(CompoundTag par1, CallbackInfo ci) {
        setGlowForSid(par1.getBoolean("sid_glow"));
    }
}
