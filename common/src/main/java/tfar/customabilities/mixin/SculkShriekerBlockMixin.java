package tfar.customabilities.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SculkSensorBlock;
import net.minecraft.world.level.block.SculkShriekerBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tfar.customabilities.Ability;
import tfar.customabilities.Constants;

@Mixin(SculkShriekerBlock.class)
public class SculkShriekerBlockMixin {

    @Inject(method = "stepOn",at = @At("HEAD"),cancellable = true)
    private void sydAbility(Level $$0, BlockPos $$1, BlockState $$2, Entity $$3, CallbackInfo ci) {
        if ($$3 instanceof Player player && Constants.hasAbility(player, Ability.Syd)) {
            ci.cancel();
        }
    }
}
