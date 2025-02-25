package tfar.customabilities.attachments;

import tfar.customabilities.CustomAbilities;
import tfar.customabilities.platform.Services;

public class CommonDataAttachments {

    public static final CommonDataAttachment<Integer> LIGHT = register(CommonDataAttachment.<Integer>create()
            .setDefaultValueSupplier(() -> 0)
            .setName(CustomAbilities.id("light")));

    static <T> CommonDataAttachment<T> register(CommonDataAttachment<T> type) {
        Services.PLATFORM.registerDataAttachment(type);
        return type;
    }

    public static void init() {

    }
}
