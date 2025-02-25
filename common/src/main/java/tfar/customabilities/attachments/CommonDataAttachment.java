package tfar.customabilities.attachments;

import com.mojang.serialization.Codec;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

public class CommonDataAttachment<T> {

    protected Supplier<T> defaultValueSupplier = () -> null;
    protected  ResourceLocation name;
    protected boolean copyOnDeath;
    protected Codec<T> codec;

    protected Object attachment;


    public static <T> CommonDataAttachment<T> create() {
        return new CommonDataAttachment<>();
    }

    public Supplier<T> getDefaultValueSupplier() {
        return defaultValueSupplier;
    }

    public CommonDataAttachment<T> setDefaultValueSupplier(Supplier<T> defaultValueSupplier) {
        this.defaultValueSupplier = defaultValueSupplier;
        return this;
    }

    public Codec<T> getCodec() {
        return codec;
    }

    public CommonDataAttachment<T> setCodec(Codec<T> codec) {
        this.codec = codec;
        return this;
    }

    public boolean isCopyOnDeath() {
        return copyOnDeath;
    }

    public CommonDataAttachment<T> copyOnDeath() {
        copyOnDeath = true;
        return this;
    }

    public ResourceLocation getName() {
        return name;
    }

    public CommonDataAttachment<T> setName(ResourceLocation name) {
        this.name = name;
        return this;
    }

    public Object getAttachment() {
        return attachment;
    }

    public CommonDataAttachment<T> setAttachment(Object attachment) {
        this.attachment = attachment;
        return this;
    }

}
