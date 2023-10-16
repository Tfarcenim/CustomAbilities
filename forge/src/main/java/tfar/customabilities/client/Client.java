package tfar.customabilities.client;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import tfar.customabilities.Ability;
import tfar.customabilities.KeyAction;
import tfar.customabilities.net.C2SKeybindPacket;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class Client {

    static Map<KeyAction, KeyMapping> mappingMap = new HashMap<>();

    public static void setupClient() {
        MinecraftForge.EVENT_BUS.addListener(Client::keyPressed);
    }

    public static void registerKeybinds(RegisterKeyMappingsEvent e) {
        e.register(ModKeybinds.INVIS_TOGGLE);
        mappingMap.put(KeyAction.TOGGLE_INVISIBILITY, ModKeybinds.INVIS_TOGGLE);
    }

    public static void keyPressed(TickEvent.ClientTickEvent e) {
        if (e.phase == TickEvent.Phase.START) {
            Player player = Minecraft.getInstance().player;
            if (player != null) {
                for (Map.Entry<KeyAction, KeyMapping> entry : mappingMap.entrySet()) {
                    KeyAction action = entry.getKey();
                    if (action.canUse(player) && entry.getValue().consumeClick()) {
                        C2SKeybindPacket.send(action);
                        return;
                    }
                }
            }
        }
    }
}
