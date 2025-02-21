package tfar.customabilities.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tfar.customabilities.CustomAbilities;
import tfar.customabilities.Hooks;

@Mixin(Player.class)
public class PlayerMixinFabric {
    @ModifyArg(method = "actuallyHurt",at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;getDamageAfterArmorAbsorb(Lnet/minecraft/world/damagesource/DamageSource;F)F"))
        private float onLivingHurt(float f, @Local(argsOnly = true) DamageSource source) {
        return CustomAbilities.onLivingHurt((LivingEntity) (Object)this,source,f);
    }

    @Inject(method = "eat",at = @At("HEAD"))
    private void eatingPlayer(Level level, ItemStack itemStack, CallbackInfoReturnable<ItemStack> cir) {
        Hooks.eatingPlayer.set((Player) (Object)this);
    }
}
