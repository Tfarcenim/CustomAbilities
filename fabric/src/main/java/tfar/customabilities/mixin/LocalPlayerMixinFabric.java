package tfar.customabilities.mixin;

import net.minecraft.client.player.Input;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tfar.customabilities.client.CustomAbilitiesClient;

@Mixin(LocalPlayer.class)
public class LocalPlayerMixinFabric {
    @Shadow public Input input;

    @Inject(method = "aiStep",at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;getTutorial()Lnet/minecraft/client/tutorial/Tutorial;"))
    private void updateInputs(CallbackInfo ci) {
        CustomAbilitiesClient.updateInputs((LocalPlayer)(Object)this,input);
    }
}
