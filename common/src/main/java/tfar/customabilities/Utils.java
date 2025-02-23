package tfar.customabilities;

import com.mojang.datafixers.util.Either;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import tfar.customabilities.ability.NewAbility;
import tfar.customabilities.platform.Services;

public class Utils {

    public static boolean hasAbility(Entity entity, NewAbility ability) {
        return Services.PLATFORM.getAbility(entity) == ability;
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
}
