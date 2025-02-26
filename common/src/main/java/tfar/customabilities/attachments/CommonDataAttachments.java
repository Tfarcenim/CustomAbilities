package tfar.customabilities.attachments;

import net.minecraft.resources.ResourceLocation;
import tfar.customabilities.CustomAbilities;
import tfar.customabilities.ability.NewAbility;
import tfar.customabilities.platform.Services;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CommonDataAttachments {

    public static final Map<ResourceLocation,CommonDataAttachment<?>> MAP =new HashMap<>();

    public static final CommonDataAttachment<Integer> LIGHT = register(CommonDataAttachment.<Integer>create()
            .setDefaultValueSupplier(() -> 0)
            .setName(CustomAbilities.id("light")));

    public static final CommonDataAttachment<NewAbility> ABILITY = register(CommonDataAttachment.<NewAbility>create()
            .setCodec(NewAbility.CODEC)
            .copyOnDeath()
            .setName(CustomAbilities.id("ability")));

    public static final CommonDataAttachment<int[]> PREVIOUS_LIGHT_LEVELS = register(CommonDataAttachment.<int[]>create()
            .setDefaultValueSupplier(CommonDataAttachments::createLightDefaults)
            .setName(CustomAbilities.id("previous_light_levels"))
    );

    public static final CommonDataAttachment<int[]> COOLDOWNS = register(CommonDataAttachment.<int[]>create()
            .setDefaultValueSupplier(() -> new int[5])
            .setName(CustomAbilities.id("cooldown")));


    static int[] createLightDefaults() {
        int[] ints = new int[40];
        Arrays.fill(ints, 15);
        return ints;
    }

    static <T> CommonDataAttachment<T> register(CommonDataAttachment<T> type) {
        Services.PLATFORM.registerDataAttachment(type);
        MAP.put(type.name,type);
        return type;
    }

    public static void init() {

    }
}
