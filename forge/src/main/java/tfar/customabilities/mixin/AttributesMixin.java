package tfar.customabilities.mixin;

import net.minecraft.world.entity.ai.attributes.Attributes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tfar.customabilities.init.ModAttributes;

@Mixin(Attributes.class)
public class AttributesMixin {
    @Inject(method = "<clinit>",at = @At("RETURN"))
    private static void init(CallbackInfo ci) {
        ModAttributes.init();
    }
}
