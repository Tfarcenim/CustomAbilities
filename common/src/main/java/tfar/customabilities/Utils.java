package tfar.customabilities;

import com.mojang.datafixers.util.Either;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import tfar.customabilities.ability.NewAbility;
import tfar.customabilities.attachments.CommonDataAttachment;
import tfar.customabilities.attachments.CommonDataAttachments;
import tfar.customabilities.network.client.S2CSyncAbilityPacket;
import tfar.customabilities.network.client.S2CSyncLightEmissionPacket;
import tfar.customabilities.platform.Services;

public class Utils {

    public static boolean hasAbility(Entity entity, NewAbility ability) {
        return getAbility(entity) == ability;
    }

    public static boolean isDark(Player player) {
        ServerLevel serverLevel = (ServerLevel) player.level();
        BlockPos pos = player.blockPosition();

        float f = player.level().hasChunkAt(player.getBlockX(), player.getBlockZ()) ? player.level().getLightLevelDependentMagicValue(BlockPos.containing(player.getX(), player.getEyeY(), player.getZ())) : 0.0F;

        return serverLevel.getBrightness(LightLayer.BLOCK, pos) < 8 && f * 15 < 8;
    }

    public static void teleportPlayerToFacing(Player player) {
        HitResult pick = player.pick(12, 0, false);
        Vec3 pos = pick.getLocation();
        teleportPlayerToLocation(player,pos);
    }

    public static void teleportPlayerToLocation(Player player, Vec3 position) {
        Either<Boolean, Vec3> eventResult = Services.PLATFORM.fireTeleportEvent(player, position.x, position.y, position.z);
        if (eventResult.right().isEmpty()) return;//the event was cancelled
        Vec3 targetPos = eventResult.right().get();
        if (player.isPassenger()) {
            player.dismountTo(position.x,position.y,position.z);
        } else {
            player.teleportTo(position.x,position.y,position.z);
        }
        player.teleportTo(targetPos.x,targetPos.y,targetPos.z);
    }

    public static boolean hasFakeElytra(LivingEntity living) {
        NewAbility ability = getAbility(living);
        return ability != null && ability.isElytra;
    }

    public static int getLightLevel(Player player) {
        Integer attachedValue = Services.PLATFORM.getAttachedValue(player, CommonDataAttachments.LIGHT);
        return attachedValue == null ? 0 : attachedValue;
    }

    public static void setLightLevel(Player player,int light) {
        Services.PLATFORM.setAttachedValue(player,CommonDataAttachments.LIGHT,light);
        if (player instanceof ServerPlayer serverPlayer) {
            Services.PLATFORM.sendToTracking(new S2CSyncLightEmissionPacket(serverPlayer.getId(),light),serverPlayer);
        }
    }

    public static void setAbility(Entity player,NewAbility ability) {
        Services.PLATFORM.setAttachedValue(player,CommonDataAttachments.ABILITY,ability);
        if (player instanceof ServerPlayer serverPlayer) {//notify player
            Services.PLATFORM.sendToClient(new S2CSyncAbilityPacket(serverPlayer.getId(),ability),serverPlayer);
        }
    }

    public static NewAbility getAbility(Entity player) {
        return Services.PLATFORM.getAttachedValue(player,CommonDataAttachments.ABILITY);
    }

    public static int[] getPreviousLightLevels(Entity entity) {
        return Services.PLATFORM.getOrCreateAttachedValue(entity,CommonDataAttachments.PREVIOUS_LIGHT_LEVELS);
    }

    public static int[] getCooldowns(Entity entity) {
        return Services.PLATFORM.getOrCreateAttachedValue(entity, CommonDataAttachments.COOLDOWNS);
    }

}
