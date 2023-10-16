package tfar.customabilities.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Minecraft.class)
public class MinecraftMixin {

    @Inject(method = "shouldEntityAppearGlowing",at = @At("RETURN"),cancellable = true)
    private void itemGlow(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        boolean alreadyGlowing = cir.getReturnValue();
        if (alreadyGlowing) return;
        if (entity instanceof ItemEntity) {
            cir.setReturnValue(true);
        }
    }
}
