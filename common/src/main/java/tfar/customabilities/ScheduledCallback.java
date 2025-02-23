package tfar.customabilities;

import net.minecraft.server.level.ServerPlayer;

import java.util.function.Consumer;

public class ScheduledCallback {

    public int timer;
    public final Consumer<ServerPlayer> consumer;

    public ScheduledCallback(int timer, Consumer<ServerPlayer> consumer){
        this.timer = timer;
        this.consumer = consumer;
    }

}
