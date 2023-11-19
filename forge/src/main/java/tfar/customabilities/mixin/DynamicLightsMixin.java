package tfar.customabilities.mixin;

import atomicstryker.dynamiclights.server.DynamicLightSourceContainer;
import atomicstryker.dynamiclights.server.DynamicLights;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import tfar.customabilities.CustomAbilitiesForge;
import tfar.customabilities.CustomDynamic;

@Mixin(DynamicLights.class)
public class DynamicLightsMixin {


    @ModifyVariable(remap = false,method = "addLightSource",at = @At(value = "INVOKE",
            target = "Ljava/util/concurrent/ConcurrentHashMap;get(Ljava/lang/Object;)Ljava/lang/Object;"))
    private static DynamicLightSourceContainer custom(DynamicLightSourceContainer light) {
        if (light.getLightSource() instanceof CustomAbilitiesForge.Light) {
            return new CustomDynamic(light.getLightSource());
        }
        return light;
    }
}
