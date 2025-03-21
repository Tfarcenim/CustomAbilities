package tfar.customabilities.network.server;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import tfar.customabilities.Utils;
import tfar.customabilities.ability.MariAbility;
import tfar.customabilities.ability.NewAbility;

public class C2SSetPercentagePacket implements C2SModPacket {

    public final double percent;

    public C2SSetPercentagePacket(FriendlyByteBuf buf) {
        percent = buf.readDouble();
    }

    public C2SSetPercentagePacket(double percent) {
        this.percent = percent;
    }

    @Override
    public void handleServer(ServerPlayer player) {
        MariAbility.chance = percent;
    }

    @Override
    public void write(FriendlyByteBuf to) {
        to.writeDouble(percent);
    }

}
