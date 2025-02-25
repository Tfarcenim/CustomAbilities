package tfar.customabilities.mixin;

import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import tfar.customabilities.EntityDuck;

@Mixin(Entity.class)
public abstract class EntityMixin implements EntityDuck {

}
