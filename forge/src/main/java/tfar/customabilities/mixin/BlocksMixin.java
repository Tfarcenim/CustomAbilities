package tfar.customabilities.mixin;

import net.minecraft.world.level.block.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tfar.customabilities.init.ModBlocks;

@Mixin(Blocks.class)
public class BlocksMixin {
    @Inject(method = "<clinit>",at = @At("RETURN"))
    private static void onInit(CallbackInfo ci) {
        ModBlocks.init();
    }
}
