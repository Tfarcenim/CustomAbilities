package tfar.customabilities.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.minecraft.world.entity.Entity;
import tfar.customabilities.init.ModEntityTypes;
import tfar.customabilities.network.client.S2CSyncDataAttachmentPacket;
import tfar.customabilities.platform.Services;

public class ClientPacketHandler {


    public static <T> void handle(S2CSyncDataAttachmentPacket<T> p) {
        ClientLevel level = Minecraft.getInstance().level;
        if (level != null) {
            Entity entity = level.getEntity(p.entityID);
            if (entity != null) {
                Services.PLATFORM.setAttachedValue(entity,p.attachment,p.value);
            }
        }
    }

    public static void renderers() {
        EntityRenderers.register(ModEntityTypes.SMOKE_CLOUD, NoopRenderer::new);
    }

}
