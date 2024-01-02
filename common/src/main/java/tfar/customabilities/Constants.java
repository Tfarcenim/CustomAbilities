package tfar.customabilities;

import com.mojang.datafixers.util.Either;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tfar.customabilities.platform.Services;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

public class Constants {

	public static final String MOD_NAME = "CustomAbilities";
	public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

	static final UUID knockbackuuid = UUID.fromString("3000b165-ed6f-49eb-8c7e-b596d4ffbb2b");

	public static AttributeModifier modifier = new AttributeModifier(knockbackuuid, "ability boost", 1, AttributeModifier.Operation.ADDITION);

	public static final Consumer<Player> NOTHING = player -> {};

	@SafeVarargs
	public static Consumer<Player> combine(Consumer<Player>... consumers) {
		return player -> Arrays.stream(consumers).forEach(consumer -> consumer.accept(player));
	}

	public static Consumer<Player> addAttributeModifier(Attribute attribute,AttributeModifier attributeModifier) {
		return player -> {
			AttributeMap attributeMap = player.getAttributes();
			AttributeInstance attributeinstance = attributeMap.getInstance(attribute);
			if (attributeinstance != null) {
				attributeinstance.removeModifier(attributeModifier);
				attributeinstance.addPermanentModifier(attributeModifier);
			}
		};
	}

	public static Consumer<Player> removeAttributeModifier(Attribute attribute,AttributeModifier attributeModifier) {
		return player -> {
			AttributeMap attributeMap = player.getAttributes();
			AttributeInstance attributeinstance = attributeMap.getInstance(attribute);
			if (attributeinstance != null) {
				attributeinstance.removePermanentModifier(attributeModifier.getId());
			}
		};
	}

	public static Consumer<Player> createPermanentEffect(MobEffect effect) {
		return player -> {
			MobEffectInstance mobeffectinstance = new MobEffectInstance(effect, -1,0,false,false);//infinite 0 amplifier effect that's not ambient and invisible
			player.addEffect(mobeffectinstance,null);
		};
	}

	public static void addStackableEffect(LivingEntity living,MobEffectInstance instance) {
		if (living.hasEffect(instance.getEffect())) {
			Map<MobEffect, MobEffectInstance> activeEffectsMap = living.getActiveEffectsMap();
			MobEffectInstance existing = activeEffectsMap.get(instance.getEffect());

			if (existing.isInfiniteDuration()) return;
			if (existing.getAmplifier() != instance.getAmplifier()) return;

			MobEffectInstance newEffect = new MobEffectInstance(instance.getEffect(),instance.getDuration() + existing.getDuration(),
					instance.getAmplifier(),instance.isAmbient(),instance.isVisible());
			living.addEffect(newEffect);
		} else {
			living.addEffect(instance);
		}
	}

	public static Consumer<Player> removePermanentEffect(MobEffect effect) {
		return player -> player.removeEffect(effect);
	}

	public static boolean hasAbility(Player player,Ability ability) {
		return ((PlayerDuck)player).getAbility() == ability;
	}

	public static boolean hasFakeElytra(LivingEntity living) {
		return living instanceof Player player && fakeElytra(player);
	}

	public static boolean fakeElytra(Player player) {
		Ability ability = ((PlayerDuck)player).getAbility();
		return ability != null && ability.fakeElytra;
	}

	public static boolean nativeAquaAffinity(Player player) {
		Ability ability = ((PlayerDuck)player).getAbility();
		return ability != null && ability.nativeAquaAffinity;
	}

	public static boolean hurtByWater(Player player) {
		Ability ability = ((PlayerDuck)player).getAbility();
		return ability != null && ability.hurtByWater;
	}

	public static void teleportMari(Player player) {
		PlayerDuck playerDuck = (PlayerDuck)player;

		HitResult pick = player.pick(20, 0, false);
		Vec3 pos = pick.getLocation();

		if (playerDuck.getAbility() == Ability.Mari) {
			int cooldown = playerDuck.getTeleportCooldown();
			if (cooldown > 0) {
				player.sendSystemMessage(Component.translatable("Teleport on Cooldown"));
				return;
			}
			LightningBolt lightningbolt = EntityType.LIGHTNING_BOLT.create(player.level());
			if (lightningbolt != null) {
				lightningbolt.moveTo(pos);
				lightningbolt.setCause((ServerPlayer) player);
				lightningbolt.setVisualOnly(true);
				player.level().addFreshEntity(lightningbolt);
				lightningbolt.playSound(SoundEvents.TRIDENT_THUNDER,5,1);
			}
			playerDuck.setTeleportCooldown(20 * 60 * 10);
		}
		teleportPlayerToLocation(player,pos);
	}

	public static void mariSpeedBoost(Player player) {
		PlayerDuck playerDuck = (PlayerDuck)player;
		if (playerDuck.getSpeedBoostCooldown()> 0) {
			player.sendSystemMessage(Component.translatable("Speed Boost on Cooldown"));
		} else {
			MobEffectInstance mobeffectinstance = new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 20 * 15, 1, true, true);
			player.addEffect(mobeffectinstance, null);
		}
	}

	public static void teleportPlayerToFacing(Player player) {
		HitResult pick = player.pick(20, 0, false);
		Vec3 pos = pick.getLocation();
		teleportPlayerToLocation(player,pos);
	}

	public static void toggleLevitation(Player player) {
		if (player.hasEffect(MobEffects.LEVITATION)) {
			player.removeEffect(MobEffects.LEVITATION);
		} else {
			player.addEffect(new MobEffectInstance(MobEffects.LEVITATION, -1,0,false,false));
		}
	}

	public static final TagKey<Item> ores = TagKey.create(Registries.ITEM, new ResourceLocation("forge","ores"));
	public static final TagKey<Item> gems = TagKey.create(Registries.ITEM, new ResourceLocation("forge","gems"));
	public static final TagKey<Item> ingots = TagKey.create(Registries.ITEM, new ResourceLocation("forge","ingots"));
	public static final TagKey<Item> raw_materials = TagKey.create(Registries.ITEM, new ResourceLocation("forge","raw_materials"));

	public static boolean isOre(ItemStack stack) {
		return stack.is(ores) || stack.is(gems) || stack.is(ingots) || stack.is(raw_materials);
	}

	public static void teleportPlayerToLocation(Player player,Vec3 position) {
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

	public static void loadAllItems(CompoundTag tag, NonNullList<ItemStack> $$1) {
		ListTag listTag = tag.getList("Items", 10);

		for(int i = 0; i < listTag.size(); ++i) {
			CompoundTag $$4 = listTag.getCompound(i);
			$$1.add(ItemStack.of($$4));
		}
	}


	public static void addItemToInv(Player player) {
		Item item = BuiltInRegistries.ITEM.get(new ResourceLocation("immersive_melodies","lute"));
		player.addItem(new ItemStack(item));
	}


	public static final int OTTY_AIR = 20 * 60 * 8;
}