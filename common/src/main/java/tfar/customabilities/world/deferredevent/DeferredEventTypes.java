package tfar.customabilities.world.deferredevent;

import net.minecraft.resources.ResourceLocation;
import tfar.customabilities.CustomAbilities;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class DeferredEventTypes {

    public static final Map<ResourceLocation,DeferredEventType<?>> REGISTRY = new HashMap<>();

    public static final DeferredEventType<AddMobEffects> SCALE_LATER =
            register(AddMobEffects::new, new ResourceLocation(CustomAbilities.MOD_ID,"scale_later"));

    public static <T extends DeferredEvent> DeferredEventType<T> register(Supplier<T> function, ResourceLocation resourceLocation) {
        DeferredEventType<T> deferredEventType = new DeferredEventType<>(function,resourceLocation);
        REGISTRY.put(resourceLocation,deferredEventType);
        return deferredEventType;
    }
}
