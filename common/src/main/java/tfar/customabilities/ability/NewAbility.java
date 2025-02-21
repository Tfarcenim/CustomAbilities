package tfar.customabilities.ability;

import com.mojang.serialization.Codec;
import net.minecraft.server.level.ServerPlayer;
import tfar.customabilities.Abilities;

public abstract class NewAbility {

    public static final Codec<NewAbility> CODEC = Codec.STRING.xmap(Abilities.ABILITIES_BY_NAME::get, NewAbility::getName);

    private String name;

    public NewAbility() {
    }

    public final String getName() {
        return name;
    }

    public final void setName(String name) {
        this.name = name;
    }

    public void tick(ServerPlayer player) {

    }

    public void onGive(ServerPlayer player) {

    }

    public void onRemove(ServerPlayer player) {

    }

}
