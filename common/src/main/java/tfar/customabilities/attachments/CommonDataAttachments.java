package tfar.customabilities.attachments;

import net.minecraft.resources.ResourceLocation;
import tfar.customabilities.CustomAbilities;
import tfar.customabilities.platform.Services;

import java.util.HashMap;
import java.util.Map;

public class CommonDataAttachments {

    public static final Map<ResourceLocation,CommonDataAttachment<?>> MAP =new HashMap<>();

    public static final CommonDataAttachment<Integer> LIGHT = register(CommonDataAttachment.<Integer>create()
            .setDefaultValueSupplier(() -> 0)
            .setName(CustomAbilities.id("light")));

    static <T> CommonDataAttachment<T> register(CommonDataAttachment<T> type) {
        Services.PLATFORM.registerDataAttachment(type);
        MAP.put(type.name,type);
        return type;
    }

    public static void init() {

    }
}
