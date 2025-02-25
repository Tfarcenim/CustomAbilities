package tfar.customabilities;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import tfar.customabilities.ability.*;

import java.util.HashMap;
import java.util.Map;

public class Abilities {
    public static final Map<String, NewAbility> ABILITIES_BY_NAME = new HashMap<>();
    public static final NewAbility BARCODE = register(new BarcodeAbility()
            .addMobEffect(new MobEffectInstance(MobEffects.SLOW_FALLING,MobEffectInstance.INFINITE_DURATION,0,false,false)),"barcode");
    public static final NewAbility SYD = register(new SydAbility()
            .addMobEffect(new MobEffectInstance(MobEffects.WATER_BREATHING,MobEffectInstance.INFINITE_DURATION,0,false,false)),"syd");
    public static final NewAbility JACKALOPE = register(new JackalopeAbility()
                    .addMobEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED,MobEffectInstance.INFINITE_DURATION,0,false,false))
                    .addMobEffect(new MobEffectInstance(MobEffects.JUMP,MobEffectInstance.INFINITE_DURATION,1,false,false))
            ,"jackalope");
    public static final NewAbility STABBERZ = register(new StabberzAbility()
    ,"stabberz");
    public static final NewAbility CUBONE = register(new CuboneAbility().providesElytra(),"cubone");

    public static final NewAbility MARI = register(new MariAbility(),"mari");


    static NewAbility register(NewAbility ability,String name) {
        ability.setName(name);
        ABILITIES_BY_NAME.put(name,ability);
        return ability;
    }

}
