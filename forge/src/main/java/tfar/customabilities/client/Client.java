package tfar.customabilities.client;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import tfar.customabilities.KeyAction;
import tfar.customabilities.net.C2SKeybindPacket;

import java.util.HashMap;
import java.util.Map;

public class Client {

    static Map<KeyAction, KeyMapping> mappingMap = new HashMap<>();

    public static void setupClient() {
        MinecraftForge.EVENT_BUS.addListener(Client::keyPressed);
    }

    public static void registerKeybinds(RegisterKeyMappingsEvent e) {
        e.register(ModKeybinds.INVIS_TOGGLE);
        e.register(ModKeybinds.TELEPORT);
        e.register(ModKeybinds.FLIGHT_BOOST);
        e.register(ModKeybinds.LEVITATION);
        e.register(ModKeybinds.BAT_MORPH);
        mappingMap.put(KeyAction.TOGGLE_INVISIBILITY, ModKeybinds.INVIS_TOGGLE);
        mappingMap.put(KeyAction.TELEPORT, ModKeybinds.TELEPORT);
        mappingMap.put(KeyAction.FLIGHT_BOOST,ModKeybinds.FLIGHT_BOOST);
        mappingMap.put(KeyAction.TOGGLE_LEVITATION,ModKeybinds.LEVITATION);
        mappingMap.put(KeyAction.BAT_MORPH,ModKeybinds.BAT_MORPH);
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
