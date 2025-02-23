package tfar.customabilities.network;

import net.minecraft.resources.ResourceLocation;
import tfar.customabilities.CustomAbilities;
import tfar.customabilities.network.server.C2SKeybindPacket;
import tfar.customabilities.platform.Services;

import java.util.Locale;

public class PacketHandler {

    public static void registerPackets() {
        Services.PLATFORM.registerServerPacket(C2SKeybindPacket.class, C2SKeybindPacket::new);

    }

    public static ResourceLocation packet(Class<?> clazz) {
        return CustomAbilities.id(clazz.getName().toLowerCase(Locale.ROOT));
    }

}
