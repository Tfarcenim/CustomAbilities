package tfar.customabilities.network.client;

import net.minecraft.network.FriendlyByteBuf;
import tfar.customabilities.attachments.CommonDataAttachment;
import tfar.customabilities.attachments.CommonDataAttachments;

public class S2CSyncBooleanDataAttachmentPacket extends S2CSyncDataAttachmentPacket<Boolean>{

    public S2CSyncBooleanDataAttachmentPacket(FriendlyByteBuf buf) {
        super(buf);
    }

    public static S2CSyncBooleanDataAttachmentPacket createPepperVisionPacket(int entityID, Boolean value) {
        return new S2CSyncBooleanDataAttachmentPacket(entityID, CommonDataAttachments.PEPPER_VISION,value);
    }

    public static S2CSyncBooleanDataAttachmentPacket createToggleElytraPacket(int entityID, Boolean value) {
        return new S2CSyncBooleanDataAttachmentPacket(entityID, CommonDataAttachments.TOGGLEABLE_ELYTRA,value);
    }

    public S2CSyncBooleanDataAttachmentPacket(int entityID, CommonDataAttachment<Boolean> attachment, Boolean value) {
        super(entityID, attachment, value);
    }

    @Override
    protected Boolean readValue(FriendlyByteBuf buf) {
        return buf.readBoolean();
    }

    @Override
    protected void writeValue(FriendlyByteBuf buf) {
        buf.writeBoolean(value);
    }
}
