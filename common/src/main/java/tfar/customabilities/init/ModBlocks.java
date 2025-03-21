package tfar.customabilities.init;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import tfar.customabilities.CustomAbilities;

public class ModBlocks {

    public static final Block f898b3 = register("f898b3",new Block(BlockBehaviour.Properties.of()));

    public static Block register(String key, Block block) {
        return Registry.register(BuiltInRegistries.BLOCK, CustomAbilities.id(key), block);
    }

    public static void init() {
    }
}
