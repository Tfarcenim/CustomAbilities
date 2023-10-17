package tfar.customabilities;

import com.mojang.datafixers.util.Either;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tfar.customabilities.platform.Services;

import java.util.UUID;
import java.util.function.Consumer;

public class Constants {

	public static final String MOD_NAME = "CustomAbilities";
	public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

	static final UUID knockbackuuid = UUID.fromString("3000b165-ed6f-49eb-8c7e-b596d4ffbb2b");

	public static AttributeModifier modifier = new AttributeModifier(knockbackuuid, "ability boost", 1, AttributeModifier.Operation.ADDITION);

	public static final Consumer<Player> NOTHING = player -> {};

	public static Consumer<Player> combine(Consumer<Player>... consumers) {
		return player -> {
			for (Consumer<Player> consumer : consumers) {
				consumer.accept(player);
			}
		};
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
			MobEffectInstance mobeffectinstance = new MobEffectInstance(effect, -1,0,true,true);//infinite 0 amplifier effect that's ambient and visible
			player.addEffect(mobeffectinstance,null);
		};
	}

	public static Consumer<Player> removePermanentEffect(MobEffect effect) {
		return player -> player.removeEffect(effect);
	}

	public static boolean hasAbility(Player player,Ability ability) {
		return ((PlayerDuck)player).getAbility() == ability;
	}

	public static boolean nativeAquaAffinity(Player player) {
		Ability ability = ((PlayerDuck)player).getAbility();
		return ability != null && ability.nativeAquaAffinity;
	}

	public static boolean hurtByWater(Player player) {
		Ability ability = ((PlayerDuck)player).getAbility();
		return ability != null && ability.hurtByWater;
	}

	public static void teleport(Player player) {
		HitResult pick = player.pick(20, 0, false);
		Vec3 pos = pick.getLocation();
		Either<Boolean, Vec3> eventResult = Services.PLATFORM.fireTeleportEvent(player, pos.x, pos.y, pos.z);
		if (eventResult.right().isEmpty()) return;//the event was cancelled
		Vec3 targetPos = eventResult.right().get();
		if (player.isPassenger()) {
			player.dismountTo(pos.x,pos.y,pos.z);
		} else {
			player.teleportTo(pos.x,pos.y,pos.z);
		}
		player.teleportTo(targetPos.x,targetPos.y,targetPos.z);
	}

	public static final int OTTY_AIR = 20 * 60 * 8;
}