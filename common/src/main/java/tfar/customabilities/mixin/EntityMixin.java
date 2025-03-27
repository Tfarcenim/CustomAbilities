package tfar.customabilities.mixin;

import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import tfar.customabilities.Abilities;
import tfar.customabilities.EntityDuck;
import tfar.customabilities.Utils;

@Mixin(Entity.class)
public abstract class EntityMixin implements EntityDuck {

    @ModifyConstant(method = "getMaxAirSupply",constant = @Constant(intValue = 300))
    public int modifyAirSupply(int constant) {
        return Utils.hasAbility((Entity)(Object)this,Abilities.CUBONE) ? 500 : 300;
    }

}
