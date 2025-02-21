package tfar.customabilities.platform;

import com.mojang.datafixers.util.Either;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import tfar.customabilities.ModAttachmentTypes;
import tfar.customabilities.ability.NewAbility;
import tfar.customabilities.platform.services.IPlatformHelper;
import net.fabricmc.loader.api.FabricLoader;

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
        player.setAttached(ModAttachmentTypes.ABILITY_DATA,ability);
    }
}
