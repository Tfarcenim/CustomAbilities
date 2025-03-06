package tfar.customabilities.platform;

import com.mojang.datafixers.util.Either;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import tfar.customabilities.CustomAbilitiesForge;
import tfar.customabilities.ScheduledCallback;
import tfar.customabilities.ability.NewAbility;
import tfar.customabilities.attachments.CommonDataAttachment;
import tfar.customabilities.net.PacketHandlerForge;
import tfar.customabilities.network.client.S2CModPacket;
import tfar.customabilities.network.server.C2SModPacket;
import tfar.customabilities.platform.services.IPlatformHelper;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;

import java.util.function.Function;

public class ForgePlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {

        return "Forge";
    }

    @Override
    public boolean isModLoaded(String modId) {

        return ModList.get().isLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {

        return !FMLLoader.isProduction();
    }

    @Override
    public Either<Boolean, Vec3> fireTeleportEvent(LivingEntity living, double x, double y, double z) {
        net.minecraftforge.event.entity.EntityTeleportEvent.EnderEntity event = net.minecraftforge.event.ForgeEventFactory.onEnderTeleport(living, x,y,z);
        if (event.isCanceled()) {
            return Either.left(true);
        }
        return Either.right(new Vec3(event.getTargetX(),event.getTargetY(),event.getTargetZ()));
    }

    @Override
    public void addAllIdentities(Player player) {
        CustomAbilitiesForge.addAllIdentities(player);
    }

    @Override
    public void removeAllIdentities(Player player) {
        CustomAbilitiesForge.removeAllIdentities(player);
    }

    int i;

    @Override
    public <MSG extends S2CModPacket> void registerClientPacket(Class<MSG> packetLocation, Function<FriendlyByteBuf, MSG> reader) {
        PacketHandlerForge.INSTANCE.registerMessage(i++, packetLocation, MSG::write, reader, PacketHandlerForge.wrapS2C());
    }

    @Override
    public <MSG extends C2SModPacket> void registerServerPacket(Class<MSG> packetLocation, Function<FriendlyByteBuf, MSG> reader) {
        PacketHandlerForge.INSTANCE.registerMessage(i++, packetLocation, MSG::write, reader, PacketHandlerForge.wrapC2S());
    }


    @Override
    public void sendToClient(S2CModPacket msg, ServerPlayer player) {
        PacketHandlerForge.sendToClient(msg, player);
    }

    @Override
    public void sendToServer(C2SModPacket msg) {
        PacketHandlerForge.sendToServer(msg);
    }

    @Override
    public void sendToTracking(S2CModPacket msg, Entity entity) {

    }

    @Override
    public void setScheduledCallback(ServerPlayer player, ScheduledCallback callback) {

    }

    @Override
    public ScheduledCallback getScheduledCallback(ServerPlayer player) {
        return null;
    }

    @Override
    public <T> void registerDataAttachment(CommonDataAttachment<T> attachment) {

    }

    @Override
    public <T> T getAttachedValue(Entity entity, CommonDataAttachment<T> attachment) {
        return null;
    }

    @Override
    public <T> void setAttachedValue(Entity entity, CommonDataAttachment<T> attachment, T value) {

    }

    @Override
    public CommonDataAttachment<?> findAttachment(ResourceLocation name) {
        return null;
    }

}