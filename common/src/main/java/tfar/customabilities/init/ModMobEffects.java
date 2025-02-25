package tfar.customabilities.init;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class ModMobEffects {
    public static final MobEffect FROSTED_FINGERS = register("frosted_fingers",new MobEffect(MobEffectCategory.BENEFICIAL,0x6666ff){});
    public static final MobEffect ELECTRO_FIST = register("electro_fist",new MobEffect(MobEffectCategory.BENEFICIAL,0xffff00){});

    private static MobEffect register(String p_19625_, MobEffect p_19626_) {
        return Registry.register(BuiltInRegistries.MOB_EFFECT, p_19625_, p_19626_);
    }

    public static void init(){}
}
