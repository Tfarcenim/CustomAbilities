package tfar.customabilities.datagen;

import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SoundDefinition;
import net.minecraftforge.common.data.SoundDefinitionsProvider;
import tfar.customabilities.CustomAbilities;
import tfar.customabilities.init.ModSoundEvents;

public class ModSoundDefinitionProvider extends SoundDefinitionsProvider {
    /**
     * Creates a new instance of this data provider.
     *
     * @param output The {@linkplain PackOutput} instance provided by the data generator.
     * @param helper The existing file helper provided by the event you are initializing this provider in.
     */
    protected ModSoundDefinitionProvider(PackOutput output, ExistingFileHelper helper) {
        super(output, CustomAbilities.MOD_ID, helper);
    }

    @Override
    public void registerSounds() {
        add(ModSoundEvents.ZAP, SoundDefinition.definition().with(sound(CustomAbilities.id("zap"))));
    }
}
