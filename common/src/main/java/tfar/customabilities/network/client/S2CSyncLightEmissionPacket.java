package tfar.customabilities.network.client;

import net.minecraft.network.FriendlyByteBuf;
import tfar.customabilities.attachments.CommonDataAttachments;

public class S2CSyncLightEmissionPacket extends S2CSyncDataAttachmentPacket<Integer>{
    public S2CSyncLightEmissionPacket(FriendlyByteBuf buf) {
        super(buf);
    }

    public S2CSyncLightEmissionPacket(int entityID, Integer value) {
        super(entityID, CommonDataAttachments.LIGHT, value);
    }

    @Override
    protected Integer readValue(FriendlyByteBuf buf) {
        return buf.readInt();
    }

    @Override
    protected void writeValue(FriendlyByteBuf buf, Integer value) {
        buf.writeInt(value);
    }
}
