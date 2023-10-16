package tfar.customabilities;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

public class Constants {

	public static final String MOD_NAME = "CustomAbilities";
	public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

	public static final Consumer<Player> NOTHING = player -> {};

	public static Consumer<Player> createPermanentEffect(MobEffect effect) {
			return player -> {
				MobEffectInstance mobeffectinstance = new MobEffectInstance(effect, -1,0,true,true);//infinite 0 amplifier effect that's ambient and visible
			player.addEffect(mobeffectinstance,null);
		};
	}

	public static Consumer<Player> removePermanentEffect(MobEffect effect) {
		return player -> player.removeEffect(effect);
	}
}