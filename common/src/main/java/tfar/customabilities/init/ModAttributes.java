package tfar.customabilities.init;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;

public class ModAttributes {

    public static final Attribute FIRE_WEAKNESS = register("generic.fire_weakness", new RangedAttribute("attribute.name.generic.fire_weakness",
            1, 0, 2048).setSyncable(true));

    public static final Attribute DROWNING_WEAKNESS = register("generic.drowning_weakness",
            new RangedAttribute("attribute.name.generic.drowning_weakness",
            1, 0, 2048).setSyncable(true));

    private static Attribute register(String p_22291_, Attribute p_22292_) {
        return Registry.register(BuiltInRegistries.ATTRIBUTE, p_22291_, p_22292_);
    }

    public static void init() {

    }

}
