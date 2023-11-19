package tfar.customabilities.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.gameevent.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tfar.customabilities.CustomAbilities;

@Mixin(targets = "net.minecraft.world.level.block.entity.SculkSensorBlockEntity$VibrationUser")
public class SculkSensorBlockEntityMixin {

    @Inject(method = "canReceiveVibration",at = @At("HEAD"),cancellable = true)
    private void sydAbility(ServerLevel pLevel, BlockPos pPos, GameEvent pGameEvent, GameEvent.Context pContext, CallbackInfoReturnable<Boolean> cir) {
        if (CustomAbilities.onSculkEvent(pLevel, pPos, pGameEvent, pContext)) {
            cir.setReturnValue(false);
        }
    }
}
