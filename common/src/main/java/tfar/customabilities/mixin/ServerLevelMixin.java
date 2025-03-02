package tfar.customabilities.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tfar.customabilities.CustomAbilities;

@Mixin(ServerLevel.class)
//@Debug(export = true)
public class ServerLevelMixin {

    @Inject(method = "findLightningTargetAround",at = @At(value = "NEW",
            target = "(Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/phys/AABB;"),cancellable = true)
    private void checkForMari(BlockPos pos, CallbackInfoReturnable<BlockPos> cir) {
        CustomAbilities.findMari((ServerLevel)(Object) this,pos).ifPresent(cir::setReturnValue);
    }
}
