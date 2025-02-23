package tfar.customabilities.platform;

import com.mojang.datafixers.util.Either;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.MixinEnvironment;
import tfar.customabilities.ModAttachmentTypes;
import tfar.customabilities.ability.NewAbility;
import tfar.customabilities.network.ClientPacketHandlerFabric;
import tfar.customabilities.network.PacketHandlerFabric;
import tfar.customabilities.network.PacketHandler;
import tfar.customabilities.network.client.S2CModPacket;
import tfar.customabilities.network.server.C2SModPacket;
import tfar.customabilities.platform.services.IPlatformHelper;
import net.fabricmc.loader.api.FabricLoader;

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
        return Either.left(true);
    }

    @Override
    public void addAllIdentities(Player player) {

    }

    @Override
    public void removeAllIdentities(Player player) {

    }

    @Override
    public NewAbility getAbility(Entity player) {
        return player.getAttached(ModAttachmentTypes.ABILITY_DATA);
    }

    @Override
    public void setAbility(Entity entity, NewAbility ability) {
        entity.setAttached(ModAttachmentTypes.ABILITY_DATA,ability);
    }

    @Override
    public int[] getCooldown(Entity entity) {
        return entity.getAttached(ModAttachmentTypes.COOLDOWN_DATA);
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
}
