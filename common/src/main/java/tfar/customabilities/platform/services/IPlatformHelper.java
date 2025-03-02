package tfar.customabilities.platform.services;

import com.mojang.datafixers.util.Either;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import tfar.customabilities.ScheduledCallback;
import tfar.customabilities.attachments.CommonDataAttachment;
import tfar.customabilities.network.client.S2CModPacket;
import tfar.customabilities.network.server.C2SModPacket;

import java.util.function.Function;

public interface IPlatformHelper {

    /**
     * Gets the name of the current platform
     *
     * @return The name of the current platform.
     */
    String getPlatformName();

    /**
     * Checks if a mod with the given id is loaded.
     *
     * @param modId The mod to check if it is loaded.
     * @return True if the mod is loaded, false otherwise.
     */
    boolean isModLoaded(String modId);

    /**
     * Check if the game is currently in a development environment.
     *
     * @return True if in a development environment, false otherwise.
     */
    boolean isDevelopmentEnvironment();

    /**
     * Gets the name of the environment type as a string.
     *
     * @return The name of the environment type.
     */
    default String getEnvironmentName() {

        return isDevelopmentEnvironment() ? "development" : "production";
    }

    Either<Boolean, Vec3> fireTeleportEvent(LivingEntity living, double x, double y, double z);

    void addAllIdentities(Player player);

    void removeAllIdentities(Player player);

    <MSG extends S2CModPacket> void registerClientPacket(Class<MSG> packetLocation, Function<FriendlyByteBuf,MSG> reader);
    <MSG extends C2SModPacket> void registerServerPacket(Class<MSG> packetLocation, Function<FriendlyByteBuf,MSG> reader);
    void sendToClient(S2CModPacket msg, ServerPlayer player);
    void sendToServer(C2SModPacket msg);
    void sendToTracking(S2CModPacket msg, Entity entity);

    void setScheduledCallback(ServerPlayer player,ScheduledCallback callback);
    ScheduledCallback getScheduledCallback(ServerPlayer player);

    <T> void registerDataAttachment(CommonDataAttachment<T> attachment);
    <T> T getAttachedValue(Entity entity,CommonDataAttachment<T> attachment);
    default <T> T getOrCreateAttachedValue(Entity entity,CommonDataAttachment<T> attachment) {
        T value = getAttachedValue(entity,attachment);
        if (value!=null) {
            return value;
        }
        setAttachedValue(entity,attachment,attachment.getDefaultValueSupplier().get());
        T newValue = getAttachedValue(entity,attachment);
        return newValue;
    }
    <T> void setAttachedValue(Entity entity,CommonDataAttachment<T> attachment,T value);

    CommonDataAttachment<?> findAttachment(ResourceLocation name);

}