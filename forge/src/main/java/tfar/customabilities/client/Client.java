package tfar.customabilities.client;

import net.minecraftforge.client.event.RegisterKeyMappingsEvent;

public class Client {

    public static void setupClient() {

    }

    public static void registerKeybinds(RegisterKeyMappingsEvent e) {
        e.register(ModKeybinds.INVIS_TOGGLE);
    }
}
