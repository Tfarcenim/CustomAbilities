package tfar.customabilities.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.data.event.GatherDataEvent;

public class ModDatagen {

    public static void start(GatherDataEvent e) {
        DataGenerator dataGenerator = e.getGenerator();
        PackOutput packOutput = dataGenerator.getPackOutput();
        boolean client = e.includeClient();
        dataGenerator.addProvider(client,new ModLangProvider(packOutput));
    }

}
