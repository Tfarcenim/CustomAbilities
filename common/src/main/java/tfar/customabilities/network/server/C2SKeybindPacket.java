package tfar.customabilities.network.server;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import tfar.customabilities.Utils;
import tfar.customabilities.ability.NewAbility;
import tfar.customabilities.platform.Services;

public class C2SKeybindPacket implements C2SModPacket {

    public final Type type;

    public C2SKeybindPacket(FriendlyByteBuf buf) {
        type = buf.readEnum(Type.class);
    }

    public C2SKeybindPacket(Type type) {
        this.type = type;
    }

    @Override
    public void handleServer(ServerPlayer player) {
        NewAbility ability = Utils.getAbility(player);
        if (ability != null) {
            ability.handleKeyPress(player, type);
        }
    }

    @Override
    public void write(FriendlyByteBuf to) {
        to.writeEnum(type);
    }

    public enum Type {
        PRIMARY,SECONDARY,TERTIARY,QUATERNARY,QUINARY
    }
}
