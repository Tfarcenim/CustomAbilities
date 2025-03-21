package tfar.customabilities.platform;

import com.mojang.datafixers.util.Either;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.impl.attachment.AttachmentRegistryImpl;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.MixinEnvironment;
import tfar.customabilities.attachments.CommonDataAttachment;
import tfar.customabilities.attachments.CommonDataAttachments;
import tfar.customabilities.init.ModAttachmentTypes;
import tfar.customabilities.ScheduledCallback;
import tfar.customabilities.ability.NewAbility;
import tfar.customabilities.network.ClientPacketHandlerFabric;
import tfar.customabilities.network.PacketHandlerFabric;
import tfar.customabilities.network.PacketHandler;
import tfar.customabilities.network.client.S2CModPacket;
import tfar.customabilities.network.server.C2SModPacket;
import tfar.customabilities.platform.services.IPlatformHelper;
import net.fabricmc.loader.api.FabricLoader;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;

public class FabricPlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "Fabric";
    }

    @Override
    public boolean isModLoaded(String modId) {

        return FabricLoader.getInstance().isModLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {

        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    @Override
    public Either<Boolean, Vec3> fireTeleportEvent(LivingEntity living, double x, double y, double z) {
        return Either.right(new Vec3(x,y,z));
    }

    @Override
    public void addAllIdentities(Player player) {

    }

    @Override
    public void removeAllIdentities(Player player) {

    }

    @Override
    public <MSG extends S2CModPacket> void registerClientPacket(Class<MSG> packetLocation, Function<FriendlyByteBuf, MSG> reader) {
        if (MixinEnvironment.getCurrentEnvironment().getSide() == MixinEnvironment.Side.CLIENT) {
            ClientPacketHandlerFabric.register(packetLocation,reader);
        }
    }

    @Override
    public <MSG extends C2SModPacket> void registerServerPacket(Class<MSG> packetLocation, Function<FriendlyByteBuf, MSG> reader) {
        ServerPlayNetworking.registerGlobalReceiver(PacketHandler.packet(packetLocation), PacketHandlerFabric.wrapC2S(reader));
    }


    @Override
    public void sendToClient(S2CModPacket msg, ServerPlayer player) {
        FriendlyByteBuf buf = PacketByteBufs.create();
        msg.write(buf);
        ServerPlayNetworking.send(player, PacketHandler.packet(msg.getClass()), buf);
    }

    @Override
    public void sendToServer(C2SModPacket msg) {
        FriendlyByteBuf buf = PacketByteBufs.create();
        msg.write(buf);
        ClientPlayNetworking.send(PacketHandler.packet(msg.getClass()), buf);
    }

    @Override
    public void sendToTracking(S2CModPacket msg, Entity entity) {
        Collection<ServerPlayer> tracking = new ArrayList<>(PlayerLookup.tracking(entity));
        if (entity instanceof ServerPlayer self) {
            sendToClient(msg,self);
        }
        for (ServerPlayer player : tracking) {
            sendToClient(msg,player);
        }
    }

    @Override
    public ScheduledCallback getScheduledCallback(ServerPlayer player) {
        return player.getAttached(ModAttachmentTypes.CALLBACK_DATA);
    }

    @Override
    public void setScheduledCallback(ServerPlayer player, ScheduledCallback callback) {
        player.setAttached(ModAttachmentTypes.CALLBACK_DATA,callback);
    }

    @SuppressWarnings("UnstableApiUsage")
    @Override
    public <T> void registerDataAttachment(CommonDataAttachment<T> attachment) {
        AttachmentType<T> type = createType(attachment);
        attachment.setAttachment(type);
    }

    @SuppressWarnings({"UnstableApiUsage", "unchecked"})
    @Override
    public <T> T getAttachedValue(Entity entity, CommonDataAttachment<T> attachment) {
        AttachmentType<T> type = (AttachmentType<T>) attachment.getAttachment();
        return entity.getAttached(type);
    }

    @SuppressWarnings({"UnstableApiUsage", "unchecked"})
    @Override
    public <T> void setAttachedValue(Entity entity, CommonDataAttachment<T> attachment, T value) {
        AttachmentType<T> type = (AttachmentType<T>) attachment.getAttachment();
        entity.setAttached(type,value);
    }

    @SuppressWarnings("UnstableApiUsage")
    <T> AttachmentType<T> createType(CommonDataAttachment<T> attachment) {
        AttachmentRegistry.Builder<T> builder = AttachmentRegistry.builder();
        if (attachment.isCopyOnDeath()) {
            builder.copyOnDeath();
        }
        if (attachment.getDefaultValueSupplier() != null) {
            builder.initializer(attachment.getDefaultValueSupplier());
        }
        if (attachment.getCodec() != null) {
            builder.persistent(attachment.getCodec());
        }
        return builder.buildAndRegister(attachment.getName());
    }

    @Override
    public CommonDataAttachment<?> findAttachment(ResourceLocation name) {
        return CommonDataAttachments.MAP.get(name);
    }
}
