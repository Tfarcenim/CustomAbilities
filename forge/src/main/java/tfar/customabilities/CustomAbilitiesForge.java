package tfar.customabilities;

import atomicstryker.dynamiclights.server.DynamicLights;
import atomicstryker.dynamiclights.server.IDynamicLightSource;
import draylar.identity.api.PlayerIdentity;
import draylar.identity.api.PlayerUnlocks;
import draylar.identity.api.platform.IdentityConfig;
import draylar.identity.api.variant.IdentityType;
import draylar.identity.impl.PlayerDataProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.VanillaGameEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.jetbrains.annotations.Nullable;
import tfar.customabilities.client.Client;
import tfar.customabilities.datagen.ModDatagen;
import tfar.customabilities.net.PacketHandler;
import tfar.customabilities.world.deferredevent.AddMobEffects;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

@Mod(CustomAbilities.MOD_ID)
public class CustomAbilitiesForge {

    public CustomAbilitiesForge() {

        // This method is invoked by the Forge mod loader when it is ready
        // to load your mod. You can access Forge and Common code in this
        // project.

        // Use Forge to bootstrap the Common mod.
        //Constants.LOG.info("Hello Forge world!");
        CustomAbilities.init();
        MinecraftForge.EVENT_BUS.addListener(this::commands);
        MinecraftForge.EVENT_BUS.addListener(this::knockback);
        MinecraftForge.EVENT_BUS.addListener(this::playertick);
        //MinecraftForge.EVENT_BUS.addListener(this::damage);
        //MinecraftForge.EVENT_BUS.addListener(this::heal);
        MinecraftForge.EVENT_BUS.addListener(this::clonePlayer);
        MinecraftForge.EVENT_BUS.addListener(this::sleepInBed);
        MinecraftForge.EVENT_BUS.addListener(this::onKill);
        MinecraftForge.EVENT_BUS.addListener(this::visibility);
        MinecraftForge.EVENT_BUS.addListener(this::vanillaEvent);
        MinecraftForge.EVENT_BUS.addListener(this::potionExpire);
        MinecraftForge.EVENT_BUS.addListener(this::worldTick);
        MinecraftForge.EVENT_BUS.addListener(this::canAffect);
        MinecraftForge.EVENT_BUS.addListener(this::onLeftClick);
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(ModDatagen::start);
        bus.addListener(this::setup);
        if (FMLEnvironment.dist.isClient()) {
            bus.addListener(this::clientSetup);
            bus.addListener(Client::registerKeybinds);
            bus.addListener(Client::registerOverlay);
        }
    }

    private void onLeftClick(PlayerInteractEvent.LeftClickBlock event) {
        ItemStack stack = event.getItemStack();
        Player player = event.getEntity();
        BlockPos pos = event.getPos();
        if (BuiltInRegistries.ITEM.getKey(stack.getItem()).equals(Constants.LUTE_RL) && player.isCrouching()) {
            Constants.triggerEvent(event.getLevel(),pos);
            event.setCanceled(true);
        }
    }
    private void canAffect(MobEffectEvent.Applicable event) {
        MobEffectInstance mobEffectInstance = event.getEffectInstance();
        LivingEntity living = event.getEntity();
        if (living instanceof Player player) {
            if (mobEffectInstance.getEffect() == MobEffects.DARKNESS && Constants.hasAbility(player,Ability.Syd)) {
                event.setResult(Event.Result.DENY);
            }
        }
    }

    private void worldTick(TickEvent.LevelTickEvent event) {
        if (event.phase == TickEvent.Phase.START && event.side == LogicalSide.SERVER) {
            ServerLevel serverLevel = (ServerLevel) event.level;
            CustomAbilities.getDeferredEventSystem(serverLevel).tickDeferredEvents(serverLevel);
        }
    }

    private void setup(FMLCommonSetupEvent e) {
        PacketHandler.registerMessages();
    }



    private void clientSetup(FMLClientSetupEvent e) {
        Client.setupClient();
    }


    private void playertick(TickEvent.PlayerTickEvent event) {
        if (event.side == LogicalSide.SERVER && event.phase == TickEvent.Phase.START) {
            Player player = event.player;
            Ability ability = ((PlayerDuck)player).getAbility();
            if (ability != null) ability.tickAbility.accept(player);
        }
    }

    private void damage(LivingDamageEvent event) {
        LivingEntity living = event.getEntity();
        if (living instanceof Player player) {
            if (Constants.hasAbility(player, Ability.Gar)) {
              //  if (lessThan25PercentHealth(player)) {
              //  }
            }
        }
    }

    private void heal(LivingHealEvent event) {
        LivingEntity living = event.getEntity();
        if (living instanceof Player player) {
            if (Constants.hasAbility(player, Ability.Gar)) {
            //    if (lessThan25PercentHealth(player)) {
              //  }
            }
        }
    }

    private void clonePlayer(PlayerEvent.Clone event) {
        Player original = event.getOriginal();
        Player player = event.getEntity();
        PlayerDuck playerDuck = (PlayerDuck)player;
        Ability ability = ((PlayerDuck)original).getAbility();
        playerDuck.setAbility(ability);

        if (event.isWasDeath()) {
            NonNullList<ItemStack> kept = ((PlayerDuck) original).getKeptItems();

            for (ItemStack stack : kept) {
                player.addItem(stack);
            }
        }

        Constants.LOG.debug("Adding abilities to player later");
        CustomAbilities.addDeferredEvent((ServerLevel) player.level(),new AddMobEffects(5, (ServerPlayer)player));
        //this is important because the player doesn't exist yet
    }

    private void sleepInBed(PlayerSleepInBedEvent event) {
        Player player = event.getEntity();
        if (Constants.hasAbility(player,Ability.Otty)) {
            int r = 3;
            List<ServerPlayer> otherPlayers = player.level().getEntitiesOfClass(ServerPlayer.class,player.getBoundingBox().inflate(r), LivingEntity::isSleeping);
            if (otherPlayers.isEmpty()) {
                event.setResult(Player.BedSleepingProblem.OTHER_PROBLEM);
            }
        }
    }

    public static void flightBoost(Player player) {
        PlayerDuck playerDuck = (PlayerDuck) player;
        Ability ability = playerDuck.getAbility();
        if (playerDuck.getFlightBoostCooldown() > 0) {
            player.sendSystemMessage(Component.translatable("Flight Boost on Cooldown"));
            return;
        }

        ItemStack firework = new ItemStack(Items.FIREWORK_ROCKET);
        FireworkRocketEntity fireworkRocketEntity = new FireworkRocketEntity(player.level(),firework,player);
        player.level().addFreshEntity(fireworkRocketEntity);
        int cooldown = 0;
        if (ability == Ability.Mari) cooldown = 30 * 20;
        else if (ability == Ability.Spriteboba) cooldown = 30 * 20;
        playerDuck.setFlightBoostCooldown(cooldown);
    }



    public static void toggleTrueInvis(Player player) {
        if (player.hasEffect(MobEffects.INVISIBILITY)) {
            player.removeEffect(MobEffects.INVISIBILITY);
        } else {
            player.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, -1,0,false,false));
        }
    }

    public static void toggleBatForm(Player player) {
        LivingEntity identity = PlayerIdentity.getIdentity(player);
        if (identity != null) {
            PlayerIdentity.updateIdentity((ServerPlayer) player, null, null);
        } else {
            EntityType<Bat> batEntityType = EntityType.BAT;
            Bat bat = batEntityType.create(player.level());
            IdentityType<?> defaultType = IdentityType.from(bat);
            if (defaultType != null) {
                PlayerIdentity.updateIdentity((ServerPlayer) player, defaultType, bat);
            }
        }
    }

    public static void addAllIdentities(Player player) {
        for (EntityType<?> entityType :BuiltInRegistries.ENTITY_TYPE) {
            if (entityType != EntityType.ENDER_DRAGON) {
                Entity entity = entityType.create(player.level());
                if (entity instanceof LivingEntity living) {
                    IdentityType<?> defaultType = IdentityType.from(living);
                    if (defaultType != null) {
                        PlayerUnlocks.unlock((ServerPlayer) player, defaultType);
                    }
                }
            }
        }
    }

    public static void removeAllIdentities(Player player) {
        PlayerDataProvider provider = (PlayerDataProvider)player;
        Set<IdentityType<?>> unlocked = new HashSet<>(provider.getUnlocked());//make a copy to avoid cc
        for (IdentityType<?> identityType : unlocked) {
            PlayerUnlocks.revoke((ServerPlayer) player,identityType);
        }
    }


    private static void equip(ServerPlayer source, ServerPlayer player, ResourceLocation identity, @Nullable CompoundTag nbt) {
        Entity created;
        if (nbt != null) {
            CompoundTag copy = nbt.copy();
            copy.putString("id", identity.toString());
            ServerLevel serverWorld = source.serverLevel();
            created = EntityType.loadEntityRecursive(copy, serverWorld, Function.identity());
        } else {
            EntityType<?> entity = BuiltInRegistries.ENTITY_TYPE.get(identity);
            created = entity.create(player.level());
        }

        if (created instanceof LivingEntity living) {
            IdentityType<?> defaultType = IdentityType.from(living);
            if (defaultType != null) {
                boolean result = PlayerIdentity.updateIdentity(player, defaultType, (LivingEntity)created);
                if (result && IdentityConfig.getInstance().logCommands()) {
                    source.displayClientMessage(Component.translatable("identity.equip_success", Component.translatable(created.getType().getDescriptionId()), player.getDisplayName()), true);
                }
            }
        }

    }

    private static void unequip(ServerPlayer source, ServerPlayer player) {
        boolean result = PlayerIdentity.updateIdentity(player, null, null);
        if (result && IdentityConfig.getInstance().logCommands()) {
            source.displayClientMessage(Component.translatable("identity.unequip_success", player.getDisplayName()), false);
        }

    }



    public static boolean hasTrueInvis(Player player) {
        return Constants.hasAbility(player,Ability.Ramsey) && player.hasEffect(MobEffects.INVISIBILITY);
    }

    //this event is crap
    private void knockback(LivingKnockBackEvent e) {
    }

    private void commands(RegisterCommandsEvent e) {
        ModCommands.register(e.getDispatcher());
    }

    private void onKill(LivingDeathEvent event) {
        Entity trueEntity = event.getSource().getEntity();
        LivingEntity died = event.getEntity();
        if (trueEntity instanceof Player player&& Constants.hasAbility(player,Ability.Ramsey) && died instanceof Player) {
            player.setAbsorptionAmount(player.getAbsorptionAmount()+2);
            ((PlayerDuck)player).setRamseyParticles(true);
            Constants.addStackableEffect(player,new MobEffectInstance(MobEffects.DAMAGE_BOOST, 20 * 60,0,true,false));
            Constants.addStackableEffect(player,new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 20 * 60,0,true,false));
        }


        if (died instanceof Player player && Constants.hasAbility(player,Ability.Muw)) {
            saveItemsMatching(player,KEEP);
        }
    }

    static final Predicate<ItemStack> KEEP = stack -> {
        return BuiltInRegistries.ITEM.getKey(stack.getItem()).equals(new ResourceLocation("immersive_melodies","lute"));
    };

    void saveItemsMatching(Player player,Predicate<ItemStack> predicate) {
        NonNullList<ItemStack> keep = NonNullList.create();
        Inventory inv = player.getInventory();
        NonNullList<ItemStack> items = inv.items;
        for (int i = 0; i < items.size(); i++) {
            ItemStack stack = items.get(i);
            if (predicate.test(stack)) {
                keep.add(stack);
                items.set(i,ItemStack.EMPTY);
            }
        }

        PlayerDuck playerDuck = (PlayerDuck) player;
        playerDuck.setKeptItems(keep);
    }

    private void potionExpire(MobEffectEvent.Expired event) {
        LivingEntity living = event.getEntity();
        if (living instanceof Player player && Constants.hasAbility(player,Ability.Ramsey)) {
            MobEffect mobEffect = event.getEffectInstance().getEffect();
            if (mobEffect == MobEffects.DAMAGE_BOOST || mobEffect == MobEffects.MOVEMENT_SPEED) {
                ((PlayerDuck)player).setRamseyParticles(false);
            }
        }
    }

    private void vanillaEvent(VanillaGameEvent event) {
        if (event.getVanillaEvent() == GameEvent.SCULK_SENSOR_TENDRILS_CLICKING) {
            GameEvent.Context context = event.getContext();
            Entity caught = context.sourceEntity();
                if (caught != null) {
                    EntityDuck entityDuck = (EntityDuck) caught;
                    entityDuck.setGlowForSid(true);
                }
        }
    }

    public static class Light implements IDynamicLightSource {

        private final Player player;
        Light(Player player) {
            this.player = player;
        }

        @Override
        public Entity getAttachmentEntity() {
            return player;
        }

        @Override
        public int getLightLevel() {
            return 15;
        }
    }

     static Map<UUID,Light> map = new HashMap<>();

    public static void toggleLights(Player player) {

        Light light = map.get(player.getUUID());

        if (light == null) {
                light = new Light(player);
                map.put(player.getUUID(), light);
                DynamicLights.addLightSource(light);
        } else {
            map.remove(player.getUUID());
            DynamicLights.removeLightSource(light);
        }
    }

    private void visibility(LivingEvent.LivingVisibilityEvent event) {
        LivingEntity living = event.getEntity();
        if (living instanceof Player player && hasTrueInvis(player)) {
            event.modifyVisibility(0);
        }
    }
}