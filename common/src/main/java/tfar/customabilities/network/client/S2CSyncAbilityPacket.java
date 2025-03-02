package tfar.customabilities.network.client;

import net.minecraft.network.FriendlyByteBuf;
import tfar.customabilities.Abilities;
import tfar.customabilities.ability.NewAbility;
import tfar.customabilities.attachments.CommonDataAttachments;

public class S2CSyncAbilityPacket extends S2CSyncDataAttachmentPacket<NewAbility> {


    public S2CSyncAbilityPacket(FriendlyByteBuf buf) {
        super(buf);
    }

    public S2CSyncAbilityPacket(int entityID, NewAbility value) {
        super(entityID, CommonDataAttachments.ABILITY, value);
    }

    @Override
    protected NewAbility readValue(FriendlyByteBuf buf) {
        String s = buf.readUtf();
        return Abilities.ABILITIES_BY_NAME.get(s);
    }

    @Override
    protected void writeValue(FriendlyByteBuf buf) {
        buf.writeUtf(value == null ? "null" : value.getName());
    }
}
