package tfar.customabilities.init;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import tfar.customabilities.CustomAbilities;

public class ModSoundEvents {
    public static final SoundEvent ZAP = register(CustomAbilities.id("zap"));

    private static SoundEvent register(String name) {
        return register(new ResourceLocation(name));
    }

    private static SoundEvent register(ResourceLocation name) {
        return register(name, name);
    }


    private static SoundEvent register(ResourceLocation name, ResourceLocation location) {
        return Registry.register(BuiltInRegistries.SOUND_EVENT, name, SoundEvent.createVariableRangeEvent(location));
    }

    public static void init() {
    }
}
