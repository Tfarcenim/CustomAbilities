package tfar.customabilities.datagen;

import net.minecraft.client.KeyMapping;
import net.minecraft.core.Holder;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.LanguageProvider;
import org.codehaus.plexus.util.StringUtils;
import tfar.customabilities.CustomAbilities;
import tfar.customabilities.Utils;
import tfar.customabilities.client.ModKeybinds;
import tfar.customabilities.init.ModAttributes;
import tfar.customabilities.init.ModMobEffects;

import java.util.function.Supplier;

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

        addKey(ModKeybinds.CHANGE_PERCENT,"Change Percent");

        addAttribute(ModAttributes.FIRE_WEAKNESS,"Fire Weakness");
        addAttribute(ModAttributes.DROWNING_WEAKNESS,"Drowning Weakness");

        Utils.getKnownMobEffects().forEach(this::addDefaultMobEffect);
    }

    void addKey(KeyMapping mapping, String translation) {
        add(mapping.getName(),translation);
    }
    void addAttribute(Attribute attribute,String translation) {
        add(attribute.getDescriptionId(),translation);
    }

    protected void addDefaultMobEffect(Holder<MobEffect> holder) {
        addEffect(holder::value,getNameFromEffect(holder.value()));
    }

    protected void addDefaultMobEffect(MobEffect effect) {
        addEffect(() -> effect,getNameFromEffect(effect));
    }

    protected void addDefaultItem(Supplier<? extends Item> supplier) {
        addItem(supplier,getNameFromItem(supplier.get()));
    }

    protected void addDefaultBlock(Supplier<? extends Block> supplier) {
        addBlock(supplier,getNameFromBlock(supplier.get()));
    }

    protected void addDefaultEntityType(Supplier<EntityType<?>> supplier) {
        addEntityType(supplier,getNameFromEntity(supplier.get()));
    }

    public static String getNameFromItem(Item item) {
        return StringUtils.capitaliseAllWords(item.getDescriptionId().split("\\.")[2].replace("_", " "));
    }

    public static String getNameFromBlock(Block block) {
        return StringUtils.capitaliseAllWords(block.getDescriptionId().split("\\.")[2].replace("_", " "));
    }

    public static String getNameFromEffect(MobEffect effect) {
        return StringUtils.capitaliseAllWords(effect.getDescriptionId().split("\\.")[2].replace("_", " "));
    }

    public static String getNameFromEntity(EntityType<?> entity) {
        return StringUtils.capitaliseAllWords(entity.getDescriptionId().split("\\.")[2].replace("_", " "));
    }

    protected void addTextComponent(MutableComponent component, String text) {
        ComponentContents contents = component.getContents();
        if (contents instanceof TranslatableContents translatableContents) {
            add(translatableContents.getKey(),text);
        } else {
            throw new UnsupportedOperationException(component +" is not translatable");
        }
    }
}
