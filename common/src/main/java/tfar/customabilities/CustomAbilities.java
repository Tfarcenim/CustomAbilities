package tfar.customabilities;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tfar.customabilities.ability.NewAbility;
import tfar.customabilities.init.ModAttributes;
import tfar.customabilities.init.ModMobEffects;
import tfar.customabilities.mixin.ItemAccessor;
import tfar.customabilities.network.PacketHandler;
import tfar.customabilities.platform.Services;

import java.util.List;
import java.util.Optional;

// This class is part of the common project meaning it is shared between all supported loaders. Code written here can only
// import and access the vanilla codebase, libraries used by vanilla, and optionally third party libraries that provide
// common compatible binaries. This means common code can not directly use loader specific concepts such as Forge events
// however it will be compatible with all supported mod loaders.
public class CustomAbilities {

    public static final String MOD_ID = "customabilities";
    public static final String MOD_NAME = "CustomAbilities";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

    // The loader specific projects are able to import and use any code from the common project. This allows you to
    // write the majority of your code here and load it from your loader specific projects. This example has some
    // code that gets invoked by the entry point of the loader specific projects.
    public static void init() {
        PacketHandler.registerPackets();
        // It is common for all supported loaders to provide a similar feature that can not be used directly in the
        // common code. A popular way to get around this is using Java's built-in service loader feature to create
        // your own abstraction layer. You can learn more about this in our provided services class. In this example
        // we have an interface in the common code and use a loader specific implementation to delegate our call to
        // the platform specific approach.

        ((ItemAccessor) Items.SUGAR).setFoodProperties(new FoodProperties.Builder().nutrition(8).saturationMod(0.5f).build());
    }

    public static ResourceLocation id(String ability) {
        return new ResourceLocation(MOD_ID, ability);
    }

    public static void tick(ServerPlayer serverPlayer) {
        NewAbility ability = Utils.getAbility(serverPlayer);
        if (ability != null) {
            ability.tick(serverPlayer);
        }
        ScheduledCallback scheduledCallback = Services.PLATFORM.getScheduledCallback(serverPlayer);
        if (scheduledCallback != null) {
            if (scheduledCallback.timer > 0) {
                scheduledCallback.timer--;
                if (scheduledCallback.timer == 0) {
                    scheduledCallback.consumer.accept(serverPlayer);
                }
            }
        }

        int[] cooldowns = Utils.getCooldowns(serverPlayer);
        boolean refresh = updateCooldowns(cooldowns);
        if (refresh) {
            Utils.setCooldowns(serverPlayer,cooldowns);
        }
    }

    static boolean updateCooldowns(int[] cooldowns) {
        boolean changed = false;
        for (int i = 0; i < cooldowns.length; i++) {
            if (cooldowns[i] > 0) {
                cooldowns[i]--;
                changed = true;
            }
        }
        return changed;
    }

    public static void insertLightLevel(Entity entity, int lightLevel) {
        int[] lightLevels = Utils.getPreviousLightLevels(entity);
        for (int i = lightLevels.length - 2; i >= 0; i--) {
            lightLevels[i + 1] = lightLevels[i];
        }
        lightLevels[0] = lightLevel;
    }

    public static float onLivingHurt(LivingEntity target, DamageSource source, float amount) {
        Entity attacker = source.getEntity();

        if (source.is(DamageTypeTags.IS_FIRE)) {
            amount *= target.getAttributeValue(ModAttributes.FIRE_WEAKNESS);
        }

        if (source.is(DamageTypeTags.IS_DROWNING)) {
            amount *= target.getAttributeValue(ModAttributes.DROWNING_WEAKNESS);
        }

        NewAbility ability = Utils.getAbility(target);
        if (ability != null) {
            amount = ability.modifyDamageTaken(target, source, amount);
        }

        if (Utils.hasAbility(target, Abilities.SYD)) {
            if (attacker instanceof LivingEntity livingAttacker) {
                if (livingAttacker.getRandom().nextDouble() < .4) {
                    livingAttacker.addEffect(new MobEffectInstance(MobEffects.POISON, 3 * 20, 0));
                }
            }
        }

        //"Frosted Fingers" Keybind - Will make Bear’s punches apply Slowness 2 and Weakness 2 for 10 seconds.
        // Repeated hits will not stack the countdown on the effects, but reset them. This keybind has a cooldown of 60 seconds.


        if (attacker instanceof LivingEntity livingAttacker) {
            if (livingAttacker.hasEffect(ModMobEffects.FROSTED_FINGERS)) {
                target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 10 * 20, 1));
                target.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 10 * 20, 1));
            }

            if (Utils.hasAbility(livingAttacker, Abilities.SUSHI)) {
                amount += 3;
            }

            if (Utils.hasAbility(livingAttacker, Abilities.KJ)) {
                if (target.getRandom().nextDouble() < .25) {
                    target.addEffect(new MobEffectInstance(MobEffects.POISON, 3 * 20, 0));
                }
            }
        }

        return amount;
    }

    public static void afterRespawn(ServerPlayer oldPlayer, ServerPlayer player, boolean alive) {
        if (!alive) {
            NewAbility ability = Utils.getAbility(player);
            if (ability != null) {
                ability.onRespawn(player);
            }
        }
    }

    public static boolean nativeAquaAffinity(LivingEntity player) {
        NewAbility ability = Utils.getAbility(player);
        return ability == Abilities.SUSHI;
    }

    public static float onLivingDamaged(LivingEntity livingEntity, DamageSource source, float f) {
        Entity attacker = source.getEntity();

        if (attacker instanceof LivingEntity livingAttacker && livingAttacker.hasEffect(ModMobEffects.ELECTRO_FIST) && livingAttacker.getMainHandItem().isEmpty()) {
            f = 5;
            if (Utils.hasAbility(attacker, Abilities.MARI)) {
                f = 8;
            }
            livingAttacker.removeEffect(ModMobEffects.ELECTRO_FIST);
        }

        return f;
    }

    public static boolean canPlayerEat(Player player, boolean canAlwaysEat, ItemStack stack) {
        boolean vanillaEat = player.canEat(canAlwaysEat);
        NewAbility ability = Utils.getAbility(player);
        return vanillaEat && (ability == null || ability.canEat(stack));

    }

    //return true to prevent damage
    public static boolean livingAttack(LivingEntity livingEntity, DamageSource source, float amount) {
        if (Utils.hasAbility(livingEntity, Abilities.DEVLIN)) {
            if (source.is(DamageTypes.FALL) && livingEntity.hasEffect(ModMobEffects.HOVERING)) return true;
            if (source.is(DamageTypeTags.IS_LIGHTNING)) {
                boolean shouldHurt = livingEntity.hasEffect(ModMobEffects.SHOCKED);
                livingEntity.addEffect(new MobEffectInstance(ModMobEffects.SHOCKED));
                return !shouldHurt;
            }
        }

        NewAbility ability = Utils.getAbility(livingEntity);
        return ability != null && ability.isImmuneTo(source);

    }

    public static float frictionEvent(LivingEntity livingEntity, float original) {
        if (livingEntity.hasEffect(ModMobEffects.HOVERING)) {
            return 1;
        }
        return original;
    }

    public static VoxelShape getShapeEvent(BlockBehaviour.BlockStateBase state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if (context != CollisionContext.empty() && context instanceof EntityCollisionContext collisionContext && state.getFluidState().is(Fluids.WATER)) {
            Entity entity = collisionContext.getEntity();
            if (entity instanceof LivingEntity livingEntity) {
                if (!livingEntity.isInWaterOrBubble() && livingEntity.hasEffect(ModMobEffects.HOVERING)) {
                    return Shapes.block();
                }
            }
        }
        return null;
    }

    public static int modifyProtection(LivingEntity livingEntity, DamageSource source, int base) {
        NewAbility newAbility = Utils.getAbility(livingEntity);
        if (newAbility != null) {
            return Math.max(base, newAbility.getNaturalProtectionPoints(livingEntity, source));
        }
        if (Utils.hasAbility(livingEntity, Abilities.PEPPER) && source.is(DamageTypes.FALL)) {
            return Math.max(12, base);
        }
        return base;
    }

    public static int getBuiltInLevel(Enchantment enchantment, LivingEntity entity) {
        NewAbility ability = Utils.getAbility(entity);
        if (ability != null) {
            return ability.getNaturalEnchantmentLevel(entity, enchantment);
        }

        return 0;
    }

    public static boolean shouldPrevent(LivingEntity entity, MobEffectInstance mobEffectInstance) {
        NewAbility ability = Utils.getAbility(entity);
        if (ability != null) {
            return ability == Abilities.BUG && mobEffectInstance.getEffect() == MobEffects.POISON;
        }
        return false;
    }

    public static Optional<BlockPos> findMari(ServerLevel level, BlockPos around) {
        Player player = level.getNearestPlayer(around.getX(),around.getY(),around.getZ(),100, (entity) -> {
            return entity != null && entity.isAlive() && level.canSeeSky(entity.blockPosition()) && Utils.hasAbility(entity,Abilities.MARI);
        });
        return Optional.ofNullable(player != null ? player.blockPosition():null);
    }
}