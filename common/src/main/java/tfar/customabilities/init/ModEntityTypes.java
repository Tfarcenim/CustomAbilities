package tfar.customabilities.init;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import tfar.customabilities.CustomAbilities;
import tfar.customabilities.entity.SmallTntEntity;
import tfar.customabilities.entity.SmokeCloudEntity;

public class ModEntityTypes {
    public static final EntityType<SmokeCloudEntity> SMOKE_CLOUD = register("smoke_cloud", EntityType.Builder.<SmokeCloudEntity>
            of(SmokeCloudEntity::new, MobCategory.MISC).fireImmune().sized(6.0F, 3F).clientTrackingRange(12).updateInterval(Integer.MAX_VALUE));

    public static final EntityType<SmallTntEntity> SMALL_TNT = register("small_tnt",EntityType.Builder.<SmallTntEntity>of(SmallTntEntity::new, MobCategory.MISC).fireImmune().sized(0.49F, 0.49F)
            .clientTrackingRange(10).updateInterval(10));

    private static <T extends Entity> EntityType<T> register(String key, EntityType.Builder<T> builder) {
        return Registry.register(BuiltInRegistries.ENTITY_TYPE, CustomAbilities.id(key), builder.build(key));
    }

    public static void init(){}

}
