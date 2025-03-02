package tfar.customabilities;

import com.mojang.datafixers.util.Either;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import tfar.customabilities.ability.NewAbility;
import tfar.customabilities.attachments.CommonDataAttachments;
import tfar.customabilities.data.DevlinAbilityData;
import tfar.customabilities.network.client.S2CSyncBooleanDataAttachmentPacket;
import tfar.customabilities.network.client.S2CSyncAbilityPacket;
import tfar.customabilities.network.client.S2CSyncLightEmissionPacket;
import tfar.customabilities.platform.Services;

import java.util.stream.Stream;

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

    public static void setCooldowns(Entity entity,int[] cooldowns) {
        Services.PLATFORM.setAttachedValue(entity,CommonDataAttachments.COOLDOWNS, cooldowns);
    }


    public static DevlinAbilityData getDelvinAbilityData(Entity entity) {
        return Services.PLATFORM.getOrCreateAttachedValue(entity, CommonDataAttachments.DEVLIN_ABILITY_DATA);
    }

    public static void setDevlinAbilityData(Entity entity,DevlinAbilityData data) {
        Services.PLATFORM.setAttachedValue(entity, CommonDataAttachments.DEVLIN_ABILITY_DATA,data);
    }

    public static void flightBoost(ServerPlayer player) {
        ItemStack firework = new ItemStack(Items.FIREWORK_ROCKET);
        FireworkRocketEntity fireworkRocketEntity = new FireworkRocketEntity(player.level(), firework, player);
        player.level().addFreshEntity(fireworkRocketEntity);
    }

    public static boolean getPepperVision(Player player) {
        return Services.PLATFORM.getOrCreateAttachedValue(player,CommonDataAttachments.PEPPER_VISION);
    }

    public static void setPepperVision(Player player,boolean pepperVision) {
        Services.PLATFORM.setAttachedValue(player,CommonDataAttachments.PEPPER_VISION,pepperVision);
        if (player instanceof ServerPlayer serverPlayer) {
            Services.PLATFORM.sendToClient(S2CSyncBooleanDataAttachmentPacket.createPepperVisionPacket(serverPlayer.getId(),pepperVision),serverPlayer);
        }
    }

    public static int getDaylightTimer(Player player) {
        return Services.PLATFORM.getOrCreateAttachedValue(player,CommonDataAttachments.DAYLIGHT_TIMER);
    }

    public static void setDaylightTimer(Player player,int pepperVision) {
        Services.PLATFORM.setAttachedValue(player,CommonDataAttachments.DAYLIGHT_TIMER,pepperVision);
    }
    public static Stream<Block> getKnownBlocks() {
        return getKnown(BuiltInRegistries.BLOCK);
    }
    public static Stream<Item> getKnownItems() {
        return getKnown(BuiltInRegistries.ITEM);
    }
    public static Stream<MobEffect> getKnownMobEffects() {
        return getKnown(BuiltInRegistries.MOB_EFFECT);
    }


    public static <V> Stream<V> getKnown(Registry<V> registry) {
        return registry.stream().filter(o -> registry.getKey(o).getNamespace().equals(CustomAbilities.MOD_ID));
    }


    public static EntityHitResult pickEntity(Entity pEntity, double pBlockInteractionRange, double pEntityInteractionRange, float pPartialTick) {
        double d0 = Math.max(pBlockInteractionRange, pEntityInteractionRange);
        double d1 = Mth.square(d0);
        Vec3 vec3 = pEntity.getEyePosition(pPartialTick);
        HitResult hitresult = pEntity.pick(d0, pPartialTick, false);
        double d2 = hitresult.getLocation().distanceToSqr(vec3);
        if (hitresult.getType() != HitResult.Type.MISS) {
            d1 = d2;
            d0 = Math.sqrt(d2);
        }

        Vec3 vec31 = pEntity.getViewVector(pPartialTick);
        Vec3 vec32 = vec3.add(vec31.x * d0, vec31.y * d0, vec31.z * d0);
        float f = 1.0F;
        AABB aabb = pEntity.getBoundingBox().expandTowards(vec31.scale(d0)).inflate(f, f, f);
        EntityHitResult entityhitresult = ProjectileUtil.getEntityHitResult(
                pEntity, vec3, vec32, aabb, entity -> !entity.isSpectator() && entity.isPickable(), d1
        );
        return entityhitresult;
    }

    public static HitResult pickEither(Entity pEntity, double pBlockInteractionRange, double pEntityInteractionRange, float pPartialTick) {
        EntityHitResult entityHitResult = pickEntity(pEntity,pBlockInteractionRange,pEntityInteractionRange,pPartialTick);
        if (entityHitResult != null) return entityHitResult;

        return pEntity.pick(pEntityInteractionRange, pPartialTick, true);
    }
}
