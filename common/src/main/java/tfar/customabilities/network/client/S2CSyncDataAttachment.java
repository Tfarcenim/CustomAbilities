package tfar.customabilities.network.client;

import net.minecraft.network.FriendlyByteBuf;
import tfar.customabilities.attachments.CommonDataAttachment;
import tfar.customabilities.attachments.CommonDataAttachments;
import tfar.customabilities.client.CLientPacketHandler;

public abstract class S2CSyncDataAttachment<T> implements S2CModPacket{

    public int entityID;
    public CommonDataAttachment<T> attachment;
    public T value;

    public S2CSyncDataAttachment(FriendlyByteBuf buf) {
        entityID = buf.readInt();
        attachment = (CommonDataAttachment<T>) CommonDataAttachments.MAP.get(buf.readResourceLocation());
        value = readValue();
    }

    protected abstract T readValue();
    protected abstract void writeValue(FriendlyByteBuf buf,T value);

    public S2CSyncDataAttachment(int entityID, CommonDataAttachment<T> attachment,T value) {
        this.entityID = entityID;
        this.attachment = attachment;
        this.value = value;
    }

    @Override
    public void handleClient() {
        CLientPacketHandler.handle(this);
    }

    @Override
    public void write(FriendlyByteBuf to) {
        to.writeInt(entityID);
        to.writeResourceLocation(attachment.getName());
        writeValue(to,value);
    }
}
