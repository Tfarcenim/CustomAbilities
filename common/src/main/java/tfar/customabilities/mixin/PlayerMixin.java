package tfar.customabilities.mixin;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity {



    
    protected PlayerMixin(EntityType<? extends LivingEntity> $$0, Level $$1) {
        super($$0, $$1);
    }
    
   /* @Override
    public int getMaxAirSupply() {
        return !constructed ? super.getMaxAirSupply() : Constants.hasAbility((Player) (Object) this, Ability.Otty) ? Constants.OTTY_AIR : super.getMaxAirSupply();
    }*/

    /*@Override
    protected int increaseAirSupply(int pCurrentAir) {
        if (getAbility() == Ability.Otty) {//otty has roughly 30x more air than normal so air should refill faster as well
            return Math.min(pCurrentAir + 120, this.getMaxAirSupply());
        }
        return super.increaseAirSupply(pCurrentAir);
    }

    @Inject(method = "readAdditionalSaveData",at = @At("RETURN"))
    private void readExtra(CompoundTag tag, CallbackInfo ci) {
        if (tag.contains("ability")) {
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
    }*/


}
