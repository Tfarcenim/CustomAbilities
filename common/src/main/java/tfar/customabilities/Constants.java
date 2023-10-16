package tfar.customabilities;

import net.minecraft.world.entity.player.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

public class Constants {

	public static final String MOD_NAME = "CustomAbilities";
	public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

	public static final Consumer<Player> NOTHING = player -> {};
}