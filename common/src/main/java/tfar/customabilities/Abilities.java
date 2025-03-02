package tfar.customabilities;

import net.minecraft.world.effect.MobEffect;
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
            .addEatImmunity(MobEffects.POISON,MobEffects.HUNGER)
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
            .addAttributeModifier(ModAttributes.FIRE_WEAKNESS,.5, AttributeModifier.Operation.MULTIPLY_TOTAL)
            .addAttributeModifier(Attributes.MAX_HEALTH,-4, AttributeModifier.Operation.ADDITION)
    );

    public static final NewAbility BEAR = register(new BearAbility("bear")
            .addMobEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST,MobEffectInstance.INFINITE_DURATION,1,false,false))
            .addMobEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE,MobEffectInstance.INFINITE_DURATION,1,false,false))

            .addAttributeModifier(Attributes.MAX_HEALTH,5, AttributeModifier.Operation.ADDITION)
            .addAttributeModifier(ModAttributes.FIRE_WEAKNESS,-.5, AttributeModifier.Operation.MULTIPLY_TOTAL)
            .addAttributeModifier(ModAttributes.DROWNING_WEAKNESS,-.5, AttributeModifier.Operation.MULTIPLY_TOTAL)
    );

    public static final NewAbility SUSHI = register(new SushiAbility("sushi")
            .addMobEffect(new MobEffectInstance(MobEffects.WATER_BREATHING,MobEffectInstance.INFINITE_DURATION,1,false,false))
            .addEatImmunity(MobEffects.POISON,MobEffects.HUNGER)
            .addAttributeModifier(ModAttributes.FIRE_WEAKNESS,.5, AttributeModifier.Operation.MULTIPLY_TOTAL)
    );

    public static final NewAbility MARI = register(new MariAbility("mari"));
    public static final NewAbility RAMSEY = register(new RamseyAbility("ramsey"));
    public static final NewAbility BRAWL = register(new BrawlAbility("brawl")
            .addMobEffect(new MobEffectInstance(MobEffects.REGENERATION,MobEffectInstance.INFINITE_DURATION,0,false,false))
            .addEatImmunity(MobEffects.POISON,MobEffects.HUNGER)
    );

    public static final NewAbility DEVLIN = register(new DevlinAbility("devlin")
            .providesElytra()
    );

    public static final NewAbility PEPPER = register(new PepperAbility("pepper")
            .addPermanentMobEffect(MobEffects.MOVEMENT_SPEED)
            .providesElytra()
    );

    public static final NewAbility KJ = register(new KJAbility("kj")
            .providesElytra()
            .addAttributeModifier(Attributes.MAX_HEALTH,4, AttributeModifier.Operation.ADDITION)
    );

    public static final NewAbility MOTH = register(new MothAbility("moth")
            .providesElytra()
            .addAttributeModifier(ModAttributes.FIRE_WEAKNESS,.25, AttributeModifier.Operation.MULTIPLY_TOTAL)
    );

    public static final NewAbility BUG = register(new BugAbility("bug")
            .addPermanentMobEffect(MobEffects.MOVEMENT_SPEED)
            .addAttributeModifier(Attributes.MAX_HEALTH,4, AttributeModifier.Operation.ADDITION)
            .addAttributeModifier(ModAttributes.FIRE_WEAKNESS,-.5, AttributeModifier.Operation.MULTIPLY_TOTAL)
            .addAttributeModifier(ModAttributes.DROWNING_WEAKNESS,.5, AttributeModifier.Operation.MULTIPLY_TOTAL)
    );

    public static final NewAbility MOPH = register(new MaphAbility("maph")
            .addAttributeModifier(Attributes.LUCK,2, AttributeModifier.Operation.ADDITION)
            .addAttributeModifier(Attributes.KNOCKBACK_RESISTANCE,.35, AttributeModifier.Operation.ADDITION)
    );

    static NewAbility register(NewAbility ability) {
        ABILITIES_BY_NAME.put(ability.getName(),ability);
        return ability;
    }

}
