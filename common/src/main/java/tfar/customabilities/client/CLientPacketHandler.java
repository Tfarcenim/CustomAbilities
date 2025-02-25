package tfar.customabilities.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.Entity;
import tfar.customabilities.network.client.S2CSyncDataAttachment;
import tfar.customabilities.platform.Services;

public class CLientPacketHandler {


    public static <T> void handle(S2CSyncDataAttachment<T> p) {
        ClientLevel level = Minecraft.getInstance().level;
        if (level != null) {
            Entity entity = level.getEntity(p.entityID);
            if (entity != null) {
                Services.PLATFORM.setAttachedValue(entity,p.attachment,p.value);
            }
        }
    }
}
