package tfar.customabilities.mixin;

import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tfar.customabilities.CustomAbilities;

@Mixin(ServerPlayer.class)
public class ServerPlayerMixinFabric {
    @Inject(method = "tick",at = @At("HEAD"))
    private void onTick(CallbackInfo ci) {
        CustomAbilities.tick((ServerPlayer)(Object)this);
    }
}
