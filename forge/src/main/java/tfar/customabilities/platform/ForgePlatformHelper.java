package tfar.customabilities.platform;

import com.mojang.datafixers.util.Either;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import tfar.customabilities.platform.services.IPlatformHelper;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;

public class ForgePlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {

        return "Forge";
    }

    @Override
    public boolean isModLoaded(String modId) {

        return ModList.get().isLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {

        return !FMLLoader.isProduction();
    }

    @Override
    public Either<Boolean, Vec3> fireTeleportEvent(LivingEntity living, double x, double y, double z) {
        net.minecraftforge.event.entity.EntityTeleportEvent.EnderEntity event = net.minecraftforge.event.ForgeEventFactory.onEnderTeleport(living, x,y,z);
        if (event.isCanceled()) {
            return Either.left(true);
        }
        return Either.right(new Vec3(event.getTargetX(),event.getTargetY(),event.getTargetZ()));
    }
}