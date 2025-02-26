package tfar.customabilities.datagen;

import net.minecraft.client.KeyMapping;
import net.minecraft.data.PackOutput;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.common.data.LanguageProvider;
import tfar.customabilities.CustomAbilities;
import tfar.customabilities.client.ModKeybinds;
import tfar.customabilities.init.ModAttributes;

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

        addAttribute(ModAttributes.FIRE_WEAKNESS,"Fire Weakness");
    }

    void addKey(KeyMapping mapping, String translation) {
        add(mapping.getName(),translation);
    }
    void addAttribute(Attribute attribute,String translation) {
        add(attribute.getDescriptionId(),translation);
    }
}
