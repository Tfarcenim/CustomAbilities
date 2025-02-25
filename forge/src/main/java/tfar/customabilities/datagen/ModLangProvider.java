package tfar.customabilities.datagen;

import net.minecraft.client.KeyMapping;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;
import tfar.customabilities.CustomAbilities;
import tfar.customabilities.client.ModKeybinds;

public class ModLangProvider extends LanguageProvider {
    public ModLangProvider(PackOutput output) {
        super(output, CustomAbilities.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add("key.categories."+ CustomAbilities.MOD_ID, CustomAbilities.MOD_NAME);
        addKey(ModKeybinds.PRIMARY,"Primary");
        addKey(ModKeybinds.SECONDARY,"Secondary");
        addKey(ModKeybinds.TERTIARY,"Tertiary");
        addKey(ModKeybinds.QUATERNARY,"Quaternary");
        addKey(ModKeybinds.QUINARY,"Quinary");
    }

    void addKey(KeyMapping mapping, String translation) {
        add(mapping.getName(),translation);
    }
}
