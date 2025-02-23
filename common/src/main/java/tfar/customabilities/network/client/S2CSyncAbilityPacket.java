package tfar.customabilities.network.client;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import tfar.customabilities.Abilities;
import tfar.customabilities.ability.NewAbility;
import tfar.customabilities.platform.Services;

public class S2CSyncAbilityPacket implements S2CModPacket{

    String ability;

    public S2CSyncAbilityPacket(String name) {
        this.ability =name;
    }

    public S2CSyncAbilityPacket(FriendlyByteBuf buf){
        ability = buf.readUtf();
    }

    @Override
    public void handleClient() {
        NewAbility ability1 = Abilities.ABILITIES_BY_NAME.get(ability);
        Services.PLATFORM.setAbility(Minecraft.getInstance().player, ability1);
    }

    @Override
    public void write(FriendlyByteBuf to) {
        to.writeUtf(ability);
    }
}
