package tfar.customabilities.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

public class ModDatagen {

    public static void start(GatherDataEvent e) {
        DataGenerator dataGenerator = e.getGenerator();
        PackOutput packOutput = dataGenerator.getPackOutput();
        ExistingFileHelper existingFileHelper = e.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = e.getLookupProvider();
        boolean client = e.includeClient();
        dataGenerator.addProvider(client,new ModLangProvider(packOutput));
        dataGenerator.addProvider(client,new ModBlockstateProvider(packOutput,existingFileHelper));

        BlockTagsProvider blockTagsProvider = new ModBlockTagsProvider(packOutput,lookupProvider,existingFileHelper);
        dataGenerator.addProvider(true,blockTagsProvider);
        dataGenerator.addProvider(true,new ModItemTagsProvider(packOutput,lookupProvider,blockTagsProvider.contentsGetter(),existingFileHelper));

        dataGenerator.addProvider(true,new ModSoundDefinitionProvider(packOutput,existingFileHelper));
    }

}
