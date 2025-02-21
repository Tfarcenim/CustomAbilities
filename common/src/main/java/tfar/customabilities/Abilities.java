package tfar.customabilities;

import tfar.customabilities.ability.BarcodeAbility;
import tfar.customabilities.ability.NewAbility;

import java.util.HashMap;
import java.util.Map;

public class Abilities {
    public static final Map<String, NewAbility> ABILITIES_BY_NAME = new HashMap<>();
    public static final NewAbility BARCODE = register(new BarcodeAbility(),"barcode");


    static NewAbility register(NewAbility ability,String name) {
        ability.setName(name);
        ABILITIES_BY_NAME.put(name,ability);
        return ability;
    }

}
