package tfar.customabilities.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import tfar.customabilities.CustomAbilities;
import tfar.customabilities.CustomAbilitiesForge;

public class Client {


    public static void setupClient() {
        MinecraftForge.EVENT_BUS.addListener(Client::keyPressed);
        MinecraftForge.EVENT_BUS.addListener(Client::renderOverlay);
        MinecraftForge.EVENT_BUS.addListener(Client::shouldRenderPlayer);
    }

    public static void shouldRenderPlayer(RenderPlayerEvent.Pre event) {
        if (CustomAbilitiesForge.hasTrueInvis(event.getEntity())) {
            event.setCanceled(true);
        }
    }

    public static void registerKeybinds(RegisterKeyMappingsEvent e) {
    }

    public static void registerOverlay(RegisterGuiOverlaysEvent event) {
        event.registerAbove(VanillaGuiOverlay.AIR_LEVEL.id(), CustomAbilities.MOD_ID, Client::handleOverlay);
    }

    public static void renderOverlay(RenderGuiOverlayEvent.Pre event) {
        if (event.getOverlay() == VanillaGuiOverlay.AIR_LEVEL.type()) {
            event.setCanceled(true);
        }
    }

    public static void handleOverlay(ForgeGui gui, GuiGraphics guiGraphics, float partialTick, int screenWidth, int screenHeight) {

        if (!gui.getMinecraft().options.hideGui && gui.shouldDrawSurvivalElements()) {
            gui.setupOverlayRenderState(true, false);

            gui.getMinecraft().getProfiler().push("customabilities_air");
            Player player = (Player) gui.getMinecraft().getCameraEntity();
            RenderSystem.enableBlend();
            int left = screenWidth / 2 + 91;
            int top = screenHeight - gui.rightHeight;

            int air = player.getAirSupply();
            int maxAir = player.getMaxAirSupply();
            if (player.isEyeInFluidType(ForgeMod.WATER_TYPE.get()) || air < maxAir) {
                int full = Mth.ceil((air - 2) * 10.0D / maxAir);
                int partial = Mth.ceil(air * 10.0D / maxAir) - full;

                for (int i = 0; i < full + partial; ++i) {
                    guiGraphics.blit(GUI_ICONS_LOCATION, left - i * 8 - 9, top, (i < full ? 16 : 25), 18, 9, 9);
                }
                gui.rightHeight += 10;
            }

            RenderSystem.disableBlend();
            gui.getMinecraft().getProfiler().pop();
        }
    }

    protected static final ResourceLocation GUI_ICONS_LOCATION = new ResourceLocation("textures/gui/icons.png");


    public static void keyPressed(TickEvent.ClientTickEvent e) {
    }
}
