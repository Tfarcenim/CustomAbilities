package tfar.customabilities.ability;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import tfar.customabilities.Utils;
import tfar.customabilities.init.ModMobEffects;

import java.util.Optional;
import java.util.function.Predicate;

//        Maph
//- Gains 1 heart of absorption per 30 seconds spent in daylight. Must have a clear view of the sky (no blocks obfuscating direct view of the sky).
// Gains Regeneration 1 and Resistance 1 when they have 1 heart of absorption
//- Gains 1 HP every 30 seconds spent in daylight. Must have a clear view of the sky (no blocks obfuscating direct view of the sky).
//        - Permanent luck 3 (No particles) when near any blocks tagged with minecraft:flowers, shareable with up to 2 nearby players.
//        naturally has +2 to their luck attribute.
//        - +3 to their burning.time attribute (burns for longer)
//- sneaking.speed should be around 0.6 - 0.7
//        - Knockback_resistance should be 0.35
//        - Has the stepheight of a horse.
public class MaphAbility extends NewAbility{
    public MaphAbility(String name) {
        super(name);
    }

    @Override
    public void tick(ServerPlayer player) {
        super.tick(player);
        if (isInDaylight(player)) {
            int daylightTimer = Utils.getDaylightTimer(player);
            daylightTimer++;
            if (daylightTimer > 600) {
                daylightTimer = 0;
                player.heal(1);
                player.setAbsorptionAmount(player.getAbsorptionAmount() +1);
            }
            Utils.setDaylightTimer(player,daylightTimer);
        }
        findNearbyFlower(player).ifPresent(pos -> {
        player.addEffect(new MobEffectInstance(MobEffects.LUCK,5,2,false,false));
    });
    }

    public static boolean isInDaylight(Player player) {
        if (player.level().isDay() && !player.level().isClientSide) {
            float f = player.getLightLevelDependentMagicValue();
            BlockPos blockpos = BlockPos.containing(player.getX(), player.getEyeY(), player.getZ());
            boolean flag = player.isInWaterRainOrBubble() || player.isInPowderSnow || player.wasInPowderSnow;
            if (f > 0.5F && !flag && player.level().canSeeSky(blockpos)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onGive(ServerPlayer player) {
        super.onGive(player);
        player.setMaxUpStep(2);
    }

    @Override
    public void onRemove(ServerPlayer player) {
        super.onRemove(player);
        player.setMaxUpStep(1);
    }


    private Optional<BlockPos> findNearbyFlower(Player player) {
        return this.findNearestBlock(player,state -> state.is(BlockTags.FLOWERS), 4);
    }

    private Optional<BlockPos> findNearestBlock(Player player,Predicate<BlockState> predicate, double distance) {
        BlockPos blockpos = player.blockPosition();
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

        for (int i = 0; (double) i <= distance; i = i > 0 ? -i : 1 - i) {
            for (int j = 0; (double) j < distance; ++j) {
                for (int k = 0; k <= j; k = k > 0 ? -k : 1 - k) {
                    for (int l = k < j && k > -j ? j : 0; l <= j; l = l > 0 ? -l : 1 - l) {
                        blockpos$mutableblockpos.setWithOffset(blockpos, k, i - 1, l);
                        if (blockpos.closerThan(blockpos$mutableblockpos, distance) && predicate.test(player.level().getBlockState(blockpos$mutableblockpos))) {
                            return Optional.of(blockpos$mutableblockpos);
                        }
                    }
                }
            }
        }
        return Optional.empty();
    }
}
