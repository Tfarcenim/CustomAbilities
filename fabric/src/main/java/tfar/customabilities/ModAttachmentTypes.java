package tfar.customabilities;

import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.server.level.ServerPlayer;
import tfar.customabilities.ability.NewAbility;

import java.util.function.Consumer;

@SuppressWarnings("UnstableApiUsage")
public class ModAttachmentTypes {

    public static final AttachmentType<NewAbility> ABILITY_DATA = AttachmentRegistry.<NewAbility>builder()
            .persistent(NewAbility.CODEC)
            .copyOnDeath()
            .buildAndRegister(CustomAbilities.id("ability"));

    public static final AttachmentType<int[]> COOLDOWN_DATA = AttachmentRegistry.<int[]>builder()
            .initializer(() -> new int[4])
            .buildAndRegister(CustomAbilities.id("cooldown"));

    public static final AttachmentType<ScheduledCallback> CALLBACK_DATA = AttachmentRegistry.<ScheduledCallback>builder()
            .initializer(() -> new ScheduledCallback(-1,player -> {}))
            .buildAndRegister(CustomAbilities.id("scheduled_callback"));

}
