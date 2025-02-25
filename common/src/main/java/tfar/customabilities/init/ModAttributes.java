package tfar.customabilities.init;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;

public class ModAttributes {

    public static final Attribute FIRE_WEAKNESS = register("generic.fire_weakness", (new RangedAttribute("attribute.name.horse.jump_strength", 0.7D, 0.0D, 2.0D)).setSyncable(true));

    private static Attribute register(String p_22291_, Attribute p_22292_) {
        return Registry.register(BuiltInRegistries.ATTRIBUTE, p_22291_, p_22292_);
    }
}
