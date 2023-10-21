package tfar.customabilities.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tfar.customabilities.Ability;
import tfar.customabilities.Constants;
import tfar.customabilities.EntityDuck;
import tfar.customabilities.PlayerDuck;

import javax.annotation.Nullable;

@Mixin(Minecraft.class)
public class MinecraftMixin {

    @Shadow @Nullable public LocalPlayer player;

    @Inject(method = "shouldEntityAppearGlowing",at = @At("RETURN"),cancellable = true)
    private void itemGlow(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        boolean alreadyGlowing = cir.getReturnValue();
        if (alreadyGlowing) return;
        if (this.player != null) {
            PlayerDuck playerDuck = (PlayerDuck)this.player;
            Ability ability = playerDuck.getAbility();

            if (ability != null) {

                switch (ability) {
                    case Saus -> {
                        if (entity instanceof ItemEntity itemEntity && Constants.isOre(itemEntity.getItem())) {
                            cir.setReturnValue(true);
                        }
                    }
                    case Gar -> {
                        if (playerDuck.garAbilityActive() && player.distanceToSqr(entity) < 25) {
                            cir.setReturnValue(true);
                        }
                    }
                    case Syd -> {
                        EntityDuck entityDuck = (EntityDuck) entity;
                        if (entityDuck.getSidGlow()) {
                            cir.setReturnValue(true);
                        }
                    }
                 }
            }
        }
    }
}
