package tfar.customabilities.ability;

import com.google.common.collect.Maps;
import com.mojang.serialization.Codec;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import tfar.customabilities.Abilities;
import tfar.customabilities.CustomAbilities;
import tfar.customabilities.Utils;
import tfar.customabilities.init.ModMobEffects;
import tfar.customabilities.network.server.C2SKeybindPacket;

import java.util.*;

public class NewAbility {

    public static final Codec<NewAbility> CODEC = Codec.STRING.xmap(Abilities.ABILITIES_BY_NAME::get, NewAbility::getName);

    private final Map<MobEffect, MobEffectInstance> mobEffects = Maps.newHashMap();
    private final Map<Attribute, AttributeModifier> attributeModifiers = Maps.newHashMap();
    private final Set<MobEffect> eatImmunities = new HashSet<>();

    private final String name;
    public boolean isElytra;

    public NewAbility(String name) {
        this.name = name;
    }

    public NewAbility addMobEffect(MobEffectInstance instance) {
        mobEffects.put(instance.getEffect(),instance);
        return this;
    }

    public NewAbility addPermanentMobEffect(MobEffect effect) {
        return addMobEffect(new MobEffectInstance(effect,MobEffectInstance.INFINITE_DURATION,0,false,false));
    };

    public NewAbility providesElytra() {
        isElytra = true;
        return this;
    }

    public final String getName() {
        return name;
    }

    public void tick(ServerPlayer player) {

    }

    public void onRespawn(ServerPlayer player) {
        onGive(player);
    }

    public void onGive(ServerPlayer player) {
        mobEffects.values().forEach(player::addEffect);
        addAttributeModifiers(player,player.getAttributes());
    }

    public void onRemove(ServerPlayer player) {
        mobEffects.keySet().forEach(player::removeEffect);
        removeAttributeModifiers(player,player.getAttributes());
    }

    public float getNightVisionModifier(Player player,float original) {
        return original;
    }

    public final void handleKeyPress(ServerPlayer player,C2SKeybindPacket.Type type) {
        int cooldown = Utils.getCooldowns(player)[type.ordinal()];

        if (cooldown > 0) {
            player.sendSystemMessage(Component.literal("This ability is on cooldown: "+cooldown/20f+ " seconds remaining"));
            return;
        }

        switch (type) {
            case PRIMARY -> handlePrimary(player);
            case SECONDARY -> handleSecondary(player);
            case TERTIARY -> handleTertiary(player);
            case QUATERNARY -> handleQuaternary(player);
            case QUINARY -> handleQuinary(player);
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

    public void handleQuinary(ServerPlayer player) {

    }

    protected static void addCooldown(ServerPlayer player,int slot,int value) {
        int[] ints = Utils.getCooldowns(player);
        ints[slot] = ints[slot] + value;
    }

    public Map<Attribute, AttributeModifier> getAttributeModifiers() {
        return this.attributeModifiers;
    }

    public NewAbility addAttributeModifier(Attribute attribute, double amount, AttributeModifier.Operation operation) {
        AttributeModifier attributemodifier = new AttributeModifier(new UUID(CustomAbilities.MOD_ID.hashCode(),name.hashCode()), this::getName, amount, operation);
        this.attributeModifiers.put(attribute, attributemodifier);
        return this;
    }

    public NewAbility addEatImmunity(MobEffect... effects) {
        eatImmunities.addAll(Arrays.asList(effects));
        return this;
    }

    public void removeAttributeModifiers(LivingEntity livingEntity, AttributeMap map) {
        for(Map.Entry<Attribute, AttributeModifier> entry : this.attributeModifiers.entrySet()) {
            AttributeInstance attributeinstance = map.getInstance(entry.getKey());
            if (attributeinstance != null) {
                attributeinstance.removeModifier(entry.getValue());
            }
        }

        if (livingEntity.getHealth() > livingEntity.getMaxHealth()) {
            livingEntity.setHealth(livingEntity.getMaxHealth());
        }
    }

    public void addAttributeModifiers(LivingEntity livingEntity, AttributeMap map) {
        for(Map.Entry<Attribute, AttributeModifier> entry : this.attributeModifiers.entrySet()) {
            AttributeInstance attributeinstance = map.getInstance(entry.getKey());
            if (attributeinstance != null) {
                AttributeModifier attributemodifier = entry.getValue();
                attributeinstance.removeModifier(attributemodifier);
                attributeinstance.addPermanentModifier(new AttributeModifier(attributemodifier.getId(), name, attributemodifier.getAmount(), attributemodifier.getOperation()));
            }
        }
        if (livingEntity.getHealth() > livingEntity.getMaxHealth()) {
            livingEntity.setHealth(livingEntity.getMaxHealth());
        }
    }

    public boolean isImmuneToFoodEffect(MobEffectInstance instance) {
        return eatImmunities.contains(instance.getEffect());
    }

    public float modifyDamageTaken(LivingEntity target, DamageSource source, float amount) {
        return amount;
    }

    public boolean canEat(ItemStack stack) {
        return stack.getItem() != Items.SUGAR;
    }

    public void onWakeup(ServerPlayer player) {

    }

    @Override
    public String toString() {
        return "NewAbility{" +
                "name='" + name + '\'' +
                '}';
    }

    public int getNaturalEnchantmentLevel(LivingEntity entity, Enchantment enchantment) {
        return 0;
    }

    public boolean shouldGlow(Player player, Entity lookingAt) {
        return false;
    }

    public int getNaturalProtectionPoints(LivingEntity livingEntity, DamageSource source) {
        return 0;
    }

    public boolean isImmuneTo(DamageSource source) {
        return false;
    }
}
