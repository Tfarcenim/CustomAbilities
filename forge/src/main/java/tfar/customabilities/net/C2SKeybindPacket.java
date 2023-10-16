package tfar.customabilities.net;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import tfar.customabilities.KeyAction;
import tfar.customabilities.net.util.C2SPacketHelper;

public class C2SKeybindPacket implements C2SPacketHelper {

    private final KeyAction action;

    public C2SKeybindPacket(KeyAction action) {
        this.action = action;
    }

    public C2SKeybindPacket(FriendlyByteBuf buf) {
        int ordinal = buf.readInt();
        action = KeyAction.values()[ordinal];
    }

    public static void send(KeyAction right) {
        PacketHandler.sendToServer(new C2SKeybindPacket(right));
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(action.ordinal());
    }

    public void handleServer(ServerPlayer player) {
        if (action.canUse(player)) {
            action.performAction(player);
        }
    }
}

