package tfar.customabilities.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.Input;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.Material;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import tfar.customabilities.Abilities;
import tfar.customabilities.Utils;
import tfar.customabilities.ability.NewAbility;
import tfar.customabilities.init.ModEntityTypes;
import tfar.customabilities.network.server.C2SKeybindPacket;
import tfar.customabilities.platform.Services;

public class CustomAbilitiesClient {


    public static void tick(Minecraft minecraft) {
        if (minecraft.level != null && !minecraft.isPaused()) {
            while (ModKeybinds.PRIMARY.consumeClick()) {
                Services.PLATFORM.sendToServer(new C2SKeybindPacket(C2SKeybindPacket.Type.PRIMARY));
            }
            while (ModKeybinds.SECONDARY.consumeClick()) {
                Services.PLATFORM.sendToServer(new C2SKeybindPacket(C2SKeybindPacket.Type.SECONDARY));
            }
            while (ModKeybinds.TERTIARY.consumeClick()) {
                Services.PLATFORM.sendToServer(new C2SKeybindPacket(C2SKeybindPacket.Type.TERTIARY));
            }
            while (ModKeybinds.QUATERNARY.consumeClick()) {
                Services.PLATFORM.sendToServer(new C2SKeybindPacket(C2SKeybindPacket.Type.QUATERNARY));
            }
            while (ModKeybinds.QUINARY.consumeClick()) {
                Services.PLATFORM.sendToServer(new C2SKeybindPacket(C2SKeybindPacket.Type.QUINARY));
            }

            if (minecraft.screen == null) {
                while (ModKeybinds.CHANGE_PERCENT.consumeClick()) {
                    minecraft.setScreen(new SetPercentageScreen(Component.literal("Shock Chance")));
                }
            }

            NewAbility ability = Utils.getAbility(minecraft.player);

            if (ability == Abilities.DEVLIN && minecraft.player.isInWater()) {
                minecraft.player.addDeltaMovement(new Vec3(0,-.020,0));
            }

            //  Services.PLATFORM.sendToServer(new C2SHoldAbilityPacket(holding_p, holding_s, holding_t, holding_q));
        }
    }

    public static float getNightVisionScale(LocalPlayer player, float old) {
        NewAbility ability = Utils.getAbility(player);
        if (ability != null) {
            return ability.getNightVisionModifier(player,old);
        }
        return old;
    }

    public static void renderers() {
        EntityRenderers.register(ModEntityTypes.SMOKE_CLOUD, NoopRenderer::new);
        EntityRenderers.register(ModEntityTypes.SMALL_TNT,SmallTntRenderer::new);
        EntityRenderers.register(ModEntityTypes.BUBBLE,BubbleRenderer::new);
    }

    public static void updateInputs(LocalPlayer localPlayer, Input input) {
        NewAbility ability = Utils.getAbility(localPlayer);
        if (ability == Abilities.BUG) {
            if (localPlayer.isInWater()) {
                input.jumping = false;
                localPlayer.addDeltaMovement(new Vec3(0,-.08,0));
            }
        }
    }
}
