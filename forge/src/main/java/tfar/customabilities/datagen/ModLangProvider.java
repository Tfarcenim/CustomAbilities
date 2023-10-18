package tfar.customabilities.datagen;

import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;
import tfar.customabilities.Constants;
import tfar.customabilities.CustomAbilities;

public class ModLangProvider extends LanguageProvider {
    public ModLangProvider(PackOutput output) {
        super(output, CustomAbilities.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add("key.categories."+ CustomAbilities.MOD_ID, Constants.MOD_NAME);
        add("key.toggle_invisibility","Toggle Invisibility");
        add("key.teleport","Teleport");
        add("key.flight_boost","Flight Boost");
    }
}
