package tfar.customabilities.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.Entity;
import tfar.customabilities.network.client.S2CSyncDataAttachmentPacket;
import tfar.customabilities.network.server.C2SKeybindPacket;
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

    public static void tick(Minecraft minecraft) {
        if (minecraft.level != null && !minecraft.isPaused()) {
            boolean holding_p = ModKeybinds.PRIMARY.isDown();
            boolean holding_s = ModKeybinds.SECONDARY.isDown();
            boolean holding_t = ModKeybinds.TERTIARY.isDown();
            boolean holding_q = ModKeybinds.QUATERNARY.isDown();
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
          //  Services.PLATFORM.sendToServer(new C2SHoldAbilityPacket(holding_p, holding_s, holding_t, holding_q));
        }

    }

}
