package tfar.customabilities.mixin;

import atomicstryker.dynamiclights.server.DynamicLightSourceContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(DynamicLightSourceContainer.class)
public interface DynamicLightSourceContainerAccess {

    @Invoker(remap = false,value = "hasEntityMoved")
    boolean hasEntityMovedAccess(Entity entity);

    @Invoker(remap = false,value = "findNewCurLightPos")
    BlockPos findNewCurLightPosAccess(Level world);

    @Invoker(remap = false,value = "addLight")
    void addLightAccess(Level world, BlockPos nextPos, int lightLevel);

    }
