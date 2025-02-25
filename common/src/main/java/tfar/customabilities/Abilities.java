package tfar.customabilities;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import tfar.customabilities.ability.*;
import tfar.customabilities.init.ModAttributes;

import java.util.HashMap;
import java.util.Map;

public class Abilities {
    public static final Map<String, NewAbility> ABILITIES_BY_NAME = new HashMap<>();
    public static final NewAbility BARCODE = register(new BarcodeAbility("barcode")
            .addMobEffect(new MobEffectInstance(MobEffects.SLOW_FALLING,MobEffectInstance.INFINITE_DURATION,0,false,false))
            .addAttributeModifier(ModAttributes.FIRE_WEAKNESS,.35, AttributeModifier.Operation.MULTIPLY_TOTAL)
    );
    public static final NewAbility SYD = register(new SydAbility("syd")
            .addMobEffect(new MobEffectInstance(MobEffects.WATER_BREATHING,MobEffectInstance.INFINITE_DURATION,0,false,false)));
    public static final NewAbility JACKALOPE = register(new JackalopeAbility("jackalope")
                    .addMobEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED,MobEffectInstance.INFINITE_DURATION,0,false,false))
                    .addMobEffect(new MobEffectInstance(MobEffects.JUMP,MobEffectInstance.INFINITE_DURATION,1,false,false))
    );
    public static final NewAbility STABBERZ = register(new StabberzAbility("stabberz")
    );
    public static final NewAbility CUBONE = register(new CuboneAbility("cubone").providesElytra()
            .addMobEffect(new MobEffectInstance(MobEffects.SLOW_FALLING,MobEffectInstance.INFINITE_DURATION,0,false,false))
            .addAttributeModifier(ModAttributes.FIRE_WEAKNESS,.5, AttributeModifier.Operation.MULTIPLY_TOTAL)
            .addAttributeModifier(Attributes.MAX_HEALTH,-4, AttributeModifier.Operation.ADDITION)
    );

    public static final NewAbility BEAR = register(new BearAbility("bear")
            .addMobEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST,MobEffectInstance.INFINITE_DURATION,1,false,false))
            .addMobEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE,MobEffectInstance.INFINITE_DURATION,1,false,false))

            .addMobEffect(new MobEffectInstance(MobEffects.NIGHT_VISION,MobEffectInstance.INFINITE_DURATION,0,false,false))

            .addAttributeModifier(Attributes.MAX_HEALTH,5, AttributeModifier.Operation.ADDITION)
            .addAttributeModifier(ModAttributes.FIRE_WEAKNESS,-.5, AttributeModifier.Operation.MULTIPLY_TOTAL)
    );

    public static final NewAbility MARI = register(new MariAbility("mari"));


    static NewAbility register(NewAbility ability) {
        ABILITIES_BY_NAME.put(ability.getName(),ability);
        return ability;
    }

}
