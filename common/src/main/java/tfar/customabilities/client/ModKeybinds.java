package tfar.customabilities.client;

import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;
import tfar.customabilities.CustomAbilities;

public class ModKeybinds {

    public static final KeyMapping INVIS_TOGGLE = new KeyMapping("key.toggle_invisibility", GLFW.GLFW_KEY_I,"key.categories."+ CustomAbilities.MOD_ID);

}