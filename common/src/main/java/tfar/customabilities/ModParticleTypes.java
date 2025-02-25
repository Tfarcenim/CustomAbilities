package tfar.customabilities;

import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;

public class ModParticleTypes {

    public static final SimpleParticleType BUBBLE = register("bubble",false);

    private static SimpleParticleType register(String path, boolean overrideLimiter) {
        return Registry.register(BuiltInRegistries.PARTICLE_TYPE,CustomAbilities.id(path), new SimpleParticleType(overrideLimiter){});
    }

    public static void init(){}

}
