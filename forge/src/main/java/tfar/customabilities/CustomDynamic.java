package tfar.customabilities;

import atomicstryker.dynamiclights.server.DynamicLightSourceContainer;
import atomicstryker.dynamiclights.server.IDynamicLightSource;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import tfar.customabilities.mixin.DynamicLightSourceContainerAccess;

public class CustomDynamic extends DynamicLightSourceContainer {
    public CustomDynamic(IDynamicLightSource light) {
        super(light);
    }

    int lastLight;

    @Override
    public boolean onUpdate() {
        Entity ent = this.getLightSource().getAttachmentEntity();
        if (!ent.isAlive()) {
            return true;
        } else {

            if (hasEntityMoved(ent)) {
                return super.onUpdate();
            }

            int currentLight = getLightSource().getLightLevel();
            if (currentLight != lastLight) {
                BlockPos nextPos = this.findNewCurLightPos(ent.level());
                if (nextPos != null) {
                    this.removeLight(ent.level());
                    this.addLight(ent.level(), nextPos, this.getLightSource().getLightLevel());
                }
            }
            lastLight = currentLight;
            return false;
        }
    }

    public BlockPos findNewCurLightPos(Level level) {
        return ((DynamicLightSourceContainerAccess) this).findNewCurLightPosAccess(level);
    }

    private void addLight(Level world, BlockPos nextPos, int lightLevel) {
        ((DynamicLightSourceContainerAccess) this).addLightAccess(world, nextPos, lightLevel);
    }

    private boolean hasEntityMoved(Entity entity) {
        return ((DynamicLightSourceContainerAccess) this).hasEntityMovedAccess(entity);
    }
}
