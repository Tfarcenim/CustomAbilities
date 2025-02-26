package tfar.customabilities.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import tfar.customabilities.Utils;
import tfar.customabilities.ability.NewAbility;
import tfar.customabilities.network.server.C2SKeybindPacket;
import tfar.customabilities.platform.Services;

public class CustomAbilitiesClient {



    public static void tick(Minecraft minecraft) {
        if (minecraft.level != null && !minecraft.isPaused()) {
            boolean holding_p = ModKeybinds.PRIMARY.isDown();
            boolean holding_s = ModKeybinds.SECONDARY.isDown();
            boolean holding_t = ModKeybinds.TERTIARY.isDown();
            boolean holding_q = ModKeybinds.QUATERNARY.isDown();
            while (ModKeybinds.PRIMARY.consumeClick()) {
                Services.PLATFORM.sendToServer(new C2SKeybindPacket(C2SKeybindPacket.Type.PRIMARY));
            }
            while (ModKeybinds.SECONDARY.consumeClick()) {
                Services.PLATFORM.sendToServer(new C2SKeybindPacket(C2SKeybindPacket.Type.SECONDARY));
            }
            while (ModKeybinds.TERTIARY.consumeClick()) {
                Services.PLATFORM.sendToServer(new C2SKeybindPacket(C2SKeybindPacket.Type.TERTIARY));
            }
            while (ModKeybinds.QUATERNARY.consumeClick()) {
                Services.PLATFORM.sendToServer(new C2SKeybindPacket(C2SKeybindPacket.Type.QUATERNARY));
            }
            while (ModKeybinds.QUINARY.consumeClick()) {
                Services.PLATFORM.sendToServer(new C2SKeybindPacket(C2SKeybindPacket.Type.QUINARY));
            }
          //  Services.PLATFORM.sendToServer(new C2SHoldAbilityPacket(holding_p, holding_s, holding_t, holding_q));
        }
    }

    public static float getNightVisionScale(LocalPlayer player, float old) {
        NewAbility ability = Utils.getAbility(player);
        if (ability != null) {
            return ability.getNightVisionModifier(player,old);
        }
        return old;
    }
}
