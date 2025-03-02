package tfar.customabilities.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record DevlinAbilityData(int hoverCharges, int boostCharges) {

    public static final Codec<DevlinAbilityData> CODEC = RecordCodecBuilder.create(instance ->
                    instance.group(//Codec.BOOL.fieldOf("hoverActive").forGetter(DevlinAbilityData::hoverActive),
                    Codec.INT.fieldOf("hoverCharges").forGetter(DevlinAbilityData::hoverCharges),
                    Codec.INT.fieldOf("boostCharges").forGetter(DevlinAbilityData::boostCharges)
                            ).apply(instance,DevlinAbilityData::new)
            );

    public static final int MAX_HOVER_CHARGES = 5;
    public static final int MAX_BOOST_CHARGES = 5;

    public static final DevlinAbilityData DEFAULTS = new DevlinAbilityData(MAX_HOVER_CHARGES,MAX_BOOST_CHARGES);

    public DevlinAbilityData consumeHoverCharge() {
        return new DevlinAbilityData(hoverCharges-1,boostCharges);
    }

    public DevlinAbilityData consumeBoostCharge() {
        return new DevlinAbilityData(hoverCharges,boostCharges-1);
    }

    public DevlinAbilityData refillCharges() {
        return new DevlinAbilityData(MAX_HOVER_CHARGES,MAX_BOOST_CHARGES);
    }
}
