package tfar.customabilities.client;

import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;
import tfar.customabilities.CustomAbilities;

public class ModKeybinds {

    public static final KeyMapping PRIMARY = new KeyMapping("key.customabilities.primary", GLFW.GLFW_MOUSE_BUTTON_4,"key.categories."+ CustomAbilities.MOD_ID);
    public static final KeyMapping SECONDARY = new KeyMapping("key.customabilities.secondary", GLFW.GLFW_KEY_Y,"key.categories."+ CustomAbilities.MOD_ID);
    public static final KeyMapping TERTIARY = new KeyMapping("key.customabilities.tertiary", GLFW.GLFW_KEY_U,"key.categories."+ CustomAbilities.MOD_ID);
    public static final KeyMapping QUATERNARY = new KeyMapping("key.customabilities.quaternary", GLFW.GLFW_KEY_L,"key.categories."+ CustomAbilities.MOD_ID);
    public static final KeyMapping QUINARY = new KeyMapping("key.customabilities.quinary", GLFW.GLFW_KEY_K,"key.categories."+ CustomAbilities.MOD_ID);


}
