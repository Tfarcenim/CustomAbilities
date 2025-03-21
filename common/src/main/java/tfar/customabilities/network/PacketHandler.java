package tfar.customabilities.network;

import net.minecraft.resources.ResourceLocation;
import tfar.customabilities.CustomAbilities;
import tfar.customabilities.network.client.S2CSyncBooleanDataAttachmentPacket;
import tfar.customabilities.network.client.S2CSyncAbilityPacket;
import tfar.customabilities.network.client.S2CSyncLightEmissionPacket;
import tfar.customabilities.network.server.C2SKeybindPacket;
import tfar.customabilities.network.server.C2SSetPercentagePacket;
import tfar.customabilities.platform.Services;

import java.util.Locale;

public class PacketHandler {

    public static void registerPackets() {
        Services.PLATFORM.registerServerPacket(C2SKeybindPacket.class, C2SKeybindPacket::new);
        Services.PLATFORM.registerServerPacket(C2SSetPercentagePacket.class, C2SSetPercentagePacket::new);

        Services.PLATFORM.registerClientPacket(S2CSyncAbilityPacket.class, S2CSyncAbilityPacket::new);
        Services.PLATFORM.registerClientPacket(S2CSyncLightEmissionPacket.class, S2CSyncLightEmissionPacket::new);

        Services.PLATFORM.registerClientPacket(S2CSyncBooleanDataAttachmentPacket.class, S2CSyncBooleanDataAttachmentPacket::new);

    }

    public static ResourceLocation packet(Class<?> clazz) {
        return CustomAbilities.id(clazz.getName().toLowerCase(Locale.ROOT));
    }

}
