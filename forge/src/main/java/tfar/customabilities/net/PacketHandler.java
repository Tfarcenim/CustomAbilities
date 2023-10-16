package tfar.customabilities.net;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import tfar.customabilities.CustomAbilities;

public class PacketHandler {

    public static SimpleChannel INSTANCE;

    public static void registerMessages() {

        INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(CustomAbilities.MOD_ID,CustomAbilities.MOD_ID), () -> "1.0", s -> true, s -> true);

        int i = 0;

        INSTANCE.registerMessage(i++,
                C2SKeybindPacket.class,
                C2SKeybindPacket::encode,
                C2SKeybindPacket::new,
                C2SKeybindPacket::handle);
    }


    public static <MSG> void sendToClient(MSG packet, ServerPlayer player) {
        INSTANCE.sendTo(packet, player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
    }

    public static <MSG> void sendToServer(MSG packet) {
        INSTANCE.sendToServer(packet);
    }
}
