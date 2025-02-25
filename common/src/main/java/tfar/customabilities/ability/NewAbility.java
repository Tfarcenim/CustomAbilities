package tfar.customabilities.ability;

import com.google.common.collect.Maps;
import com.mojang.serialization.Codec;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import tfar.customabilities.Abilities;
import tfar.customabilities.network.server.C2SKeybindPacket;
import tfar.customabilities.platform.Services;

import java.util.Map;

public abstract class NewAbility {

    public static final Codec<NewAbility> CODEC = Codec.STRING.xmap(Abilities.ABILITIES_BY_NAME::get, NewAbility::getName);

    private final Map<MobEffect, MobEffectInstance> mobEffects = Maps.newHashMap();

    private String name;
    public boolean isElytra;

    public NewAbility() {
    }

    public NewAbility addMobEffect(MobEffectInstance instance) {
        mobEffects.put(instance.getEffect(),instance);
        return this;
    }

    public NewAbility providesElytra() {
        isElytra = true;
        return this;
    }

    public final String getName() {
        return name;
    }

    public final void setName(String name) {
        this.name = name;
    }

    public void tick(ServerPlayer player) {

    }

    public void onRespawn(ServerPlayer player) {
        onGive(player);
    }

    public void onGive(ServerPlayer player) {
        mobEffects.values().forEach(player::addEffect);
    }

    public void onRemove(ServerPlayer player) {
        mobEffects.keySet().forEach(player::removeEffect);
    }

    public final void handleKeyPress(ServerPlayer player,C2SKeybindPacket.Type type) {
        switch (type) {
            case PRIMARY -> handlePrimary(player);
            case SECONDARY -> handleSecondary(player);
            case TERTIARY -> handleTertiary(player);
            case QUATERNARY -> handleQuaternary(player);
        }
    }

    public void handlePrimary(ServerPlayer player) {

    }

    public void handleSecondary(ServerPlayer player) {

    }

    public void handleTertiary(ServerPlayer player) {

    }

    public void handleQuaternary(ServerPlayer player) {

    }

    protected static void addCooldown(ServerPlayer player,int slot,int value) {
        int[] ints = Services.PLATFORM.getCooldown(player);
        ints[slot] = ints[slot] + value;
    }

}
