package tfar.customabilities.datagen;

import net.minecraft.client.KeyMapping;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;
import tfar.customabilities.Constants;
import tfar.customabilities.CustomAbilities;
import tfar.customabilities.client.ModKeybinds;

public class ModLangProvider extends LanguageProvider {
    public ModLangProvider(PackOutput output) {
        super(output, CustomAbilities.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add("key.categories."+ CustomAbilities.MOD_ID, Constants.MOD_NAME);
        addKey(ModKeybinds.INVIS_TOGGLE,"Toggle Invisibility");
        addKey(ModKeybinds.TELEPORT,"Teleport (Mari & Miblex)");
        addKey(ModKeybinds.FLIGHT_BOOST,"Flight Boost");
        addKey(ModKeybinds.BAT_MORPH,"Bat Morph (Barcode)");
        addKey(ModKeybinds.LEVITATION,"Levitation (Bug)");
        addKey(ModKeybinds.SPEED_BOOST,"Speed Boost");
        addKey(ModKeybinds.LIGHT_TOGGLE,"Light Toggle (Bug)");
    }

    void addKey(KeyMapping mapping, String translation) {
        add(mapping.getName(),translation);
    }
}
