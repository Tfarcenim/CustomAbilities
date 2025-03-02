package tfar.customabilities.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tfar.customabilities.Constants;
import tfar.customabilities.EntityDuck;
import tfar.customabilities.Utils;
import tfar.customabilities.ability.NewAbility;
import tfar.customabilities.platform.Services;

import javax.annotation.Nullable;

@Mixin(Minecraft.class)
public class MinecraftMixin {

    @Shadow @Nullable public LocalPlayer player;

    @Inject(method = "shouldEntityAppearGlowing",at = @At("RETURN"),cancellable = true)
    private void itemGlow(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        boolean alreadyGlowing = cir.getReturnValue();
        if (alreadyGlowing) return;
        if (this.player != null) {
            NewAbility ability = Utils.getAbility(this.player);
            if (ability != null) {
                boolean shouldGlow = ability.shouldGlow(this.player,entity);
                if (shouldGlow) {
                    cir.setReturnValue(true);
                }
            }
        }
    }
}
