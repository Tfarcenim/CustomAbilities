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
        registerAbilityKeybind(e,KeyAction.TOGGLE_INVISIBILITY, ModKeybinds.INVIS_TOGGLE);
        registerAbilityKeybind(e,KeyAction.TELEPORT, ModKeybinds.TELEPORT);
        registerAbilityKeybind(e,KeyAction.FLIGHT_BOOST,ModKeybinds.FLIGHT_BOOST);
        registerAbilityKeybind(e,KeyAction.TOGGLE_LEVITATION,ModKeybinds.LEVITATION);
        registerAbilityKeybind(e,KeyAction.BAT_MORPH,ModKeybinds.BAT_MORPH);
        registerAbilityKeybind(e,KeyAction.SPEED_BOOST,ModKeybinds.SPEED_BOOST);
        registerAbilityKeybind(e,KeyAction.LIGHT_TOGGLE,ModKeybinds.LIGHT_TOGGLE);
    }

    private static void registerAbilityKeybind(RegisterKeyMappingsEvent e,KeyAction keyAction,KeyMapping keyBind) {
        e.register(keyBind);
        mappingMap.put(keyAction,keyBind);
    }

    public static void keyPressed(TickEvent.ClientTickEvent e) {
        if (e.phase == TickEvent.Phase.START) {
            Player player = Minecraft.getInstance().player;
            if (player != null && Minecraft.getInstance().level.getGameTime() %2 == 0) {
                for (Map.Entry<KeyAction, KeyMapping> entry : mappingMap.entrySet()) {
                    KeyAction action = entry.getKey();
                    if (action.canUse(player) && entry.getValue().isDown()) {
                        C2SKeybindPacket.send(action);
                        return;
                    }
                }
            }
        }
    }
}
