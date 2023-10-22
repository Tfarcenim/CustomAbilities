package tfar.customabilities.client;

import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;
import tfar.customabilities.CustomAbilities;

public class ModKeybinds {

    public static final KeyMapping INVIS_TOGGLE = new KeyMapping("key.toggle_invisibility", GLFW.GLFW_KEY_I,"key.categories."+ CustomAbilities.MOD_ID);
    public static final KeyMapping TELEPORT = new KeyMapping("key.teleport", GLFW.GLFW_KEY_Y,"key.categories."+ CustomAbilities.MOD_ID);
    public static final KeyMapping FLIGHT_BOOST = new KeyMapping("key.flight_boost", GLFW.GLFW_KEY_U,"key.categories."+ CustomAbilities.MOD_ID);
    public static final KeyMapping LEVITATION = new KeyMapping("key.levitation", GLFW.GLFW_KEY_L,"key.categories."+ CustomAbilities.MOD_ID);
    public static final KeyMapping BAT_MORPH = new KeyMapping("key.bat_morph", GLFW.GLFW_KEY_B,"key.categories."+ CustomAbilities.MOD_ID);
    public static final KeyMapping SPEED_BOOST = new KeyMapping("key.speed_boost", GLFW.GLFW_KEY_B,"key.categories."+ CustomAbilities.MOD_ID);
    public static final KeyMapping LIGHT_TOGGLE = new KeyMapping("key.light_toggle", GLFW.GLFW_KEY_L,"key.categories."+ CustomAbilities.MOD_ID);


}
