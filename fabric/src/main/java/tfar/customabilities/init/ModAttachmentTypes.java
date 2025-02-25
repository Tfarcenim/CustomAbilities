package tfar.customabilities.init;

import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import tfar.customabilities.CustomAbilities;
import tfar.customabilities.ScheduledCallback;
import tfar.customabilities.ability.NewAbility;

@SuppressWarnings("UnstableApiUsage")
public class ModAttachmentTypes {

    public static final AttachmentType<int[]> COOLDOWN_DATA = AttachmentRegistry.<int[]>builder()
            .initializer(() -> new int[4])
            .buildAndRegister(CustomAbilities.id("cooldown"));

    public static final AttachmentType<ScheduledCallback> CALLBACK_DATA = AttachmentRegistry.<ScheduledCallback>builder()
            .initializer(() -> new ScheduledCallback(-1,player -> {}))
            .buildAndRegister(CustomAbilities.id("scheduled_callback"));

}
