package tfar.customabilities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.VanillaGameEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import tfar.customabilities.client.Client;
import tfar.customabilities.datagen.ModDatagen;
import tfar.customabilities.net.PacketHandler;

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
        MinecraftForge.EVENT_BUS.addListener(this::attack);
        //MinecraftForge.EVENT_BUS.addListener(this::heal);
        MinecraftForge.EVENT_BUS.addListener(this::clonePlayer);
        MinecraftForge.EVENT_BUS.addListener(this::onKill);
        MinecraftForge.EVENT_BUS.addListener(this::visibility);
        MinecraftForge.EVENT_BUS.addListener(this::vanillaEvent);
        MinecraftForge.EVENT_BUS.addListener(this::potionExpire);
        MinecraftForge.EVENT_BUS.addListener(this::canAffect);
        MinecraftForge.EVENT_BUS.addListener(this::onLeftClickBlock);
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(ModDatagen::start);
        bus.addListener(this::setup);
        if (FMLEnvironment.dist.isClient()) {
            bus.addListener(this::clientSetup);
            bus.addListener(Client::registerKeybinds);
            bus.addListener(Client::registerOverlay);
        }
    }



    private void onLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
        ItemStack stack = event.getItemStack();
        Player player = event.getEntity();
        BlockPos pos = event.getPos();
        Level level = event.getLevel();
        ResourceLocation rl = BuiltInRegistries.ITEM.getKey(stack.getItem());
        if (player.isCrouching()) {
            boolean cooldown = player.getCooldowns().isOnCooldown(stack.getItem());
            if (!cooldown) {
                if (rl.equals(Constants.LUTE_RL)) {
                    Constants.triggerEvent(event.getLevel(), pos.getX(),pos.getY(),pos.getZ());
                    player.getCooldowns().addCooldown(stack.getItem(), 100);
                    if (!level.isClientSide) {
                        makeAreaOfEffectCloud(player, Potions.STRONG_REGENERATION, pos.getX(),pos.getY(),pos.getZ());
                    }
                  //  event.setCanceled(true);
                } else if (rl.equals(Constants.GUITAR_RL)) {
                    Constants.triggerEvent(event.getLevel(), pos.getX(),pos.getY(),pos.getZ());
                    player.getCooldowns().addCooldown(stack.getItem(), 100);
                    if (!level.isClientSide) {
                        makeShockwave(player);
                    }
                   // event.setCanceled(true);
                }
            }
        }
    }


    private static void makeShockwave(LivingEntity living) {
        //this.level.gameEvent(this.source, GameEvent.EXPLODE, new Vec3(this.x, this.y, this.z));
        float f2 = 16;
        Vec3 vec3 = living.position();
        List<Entity> list = living.level().getEntities(living, new AABB(vec3.add(-2, -2, -2), vec3.add(2, 2, 2)));

        for (int k2 = 0; k2 < list.size(); ++k2) {
            Entity entity = list.get(k2);
            if (!entity.ignoreExplosion()) {
                double distX = entity.getX() - living.getX();
                double distY = (entity instanceof PrimedTnt ? entity.getY() : entity.getEyeY()) - living.getY();
                double distZ = entity.getZ() - living.getZ();
                Vec3 motion = new Vec3(distX, distY, distZ).normalize();
                entity.setDeltaMovement(entity.getDeltaMovement().add(motion));
                if (entity instanceof Player player) {
                    if (!player.isSpectator() && (!player.isCreative() || !player.getAbilities().flying)) {
                        player.hurtMarked = true;
                    }
                }
            }
        }
    }

    private static void makeAreaOfEffectCloud(LivingEntity entity, Potion pPotion, double x,double y,double z) {
        AreaEffectCloud areaeffectcloud = new AreaEffectCloud(entity.level(), x +.5,y + 1, z+.5);
        areaeffectcloud.setOwner(entity);
        areaeffectcloud.setRadius(3.0F);
        areaeffectcloud.setRadiusOnUse(-0.5F);
        areaeffectcloud.setWaitTime(10);
        areaeffectcloud.setRadiusPerTick(-areaeffectcloud.getRadius() / (float) areaeffectcloud.getDuration());
        areaeffectcloud.setPotion(pPotion);


        entity.level().addFreshEntity(areaeffectcloud);
    }

    private void canAffect(MobEffectEvent.Applicable event) {
        MobEffectInstance mobEffectInstance = event.getEffectInstance();
        LivingEntity living = event.getEntity();
        if (living instanceof Player player) {
            if (mobEffectInstance.getEffect() == MobEffects.DARKNESS) {
                event.setResult(Event.Result.DENY);
            }
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

        }
    }

    private void attack(LivingAttackEvent event) {
        LivingEntity living = event.getEntity();
        DamageSource source = event.getSource();
        Level level = living.level();
        Vec3 pos = living.position();
        Entity attacker = source.getEntity();
        if (attacker instanceof Player player) {
            ItemStack stack = player.getMainHandItem();
            ResourceLocation rl = BuiltInRegistries.ITEM.getKey(stack.getItem());
            if (player.isCrouching()) {
                boolean cooldown = player.getCooldowns().isOnCooldown(stack.getItem());
                if (!cooldown) {
                    if (rl.equals(Constants.LUTE_RL)) {
                        Constants.triggerEvent(level, pos.x,pos.y,pos.z);
                        player.getCooldowns().addCooldown(stack.getItem(), 100);
                        if (!level.isClientSide) {
                            makeAreaOfEffectCloud(player, Potions.STRONG_REGENERATION, pos.x,pos.y,pos.z);
                        }
                        event.setCanceled(true);
                    } else if (rl.equals(Constants.GUITAR_RL)) {
                        Constants.triggerEvent(level, pos.x,pos.y,pos.z);
                        player.getCooldowns().addCooldown(stack.getItem(), 100);
                        if (!level.isClientSide) {
                            makeShockwave(player);
                        }
                        event.setCanceled(true);
                    }
                }
            }
        }
    }

    private void clonePlayer(PlayerEvent.Clone event) {
        Player original = event.getOriginal();
        Player player = event.getEntity();

    //    if (event.isWasDeath()) {
    //        NonNullList<ItemStack> kept = ((PlayerDuck) original).getKeptItems();

   //         for (ItemStack stack : kept) {
   //             player.addItem(stack);
     //       }
    //    }
    }

    public static void flightBoost(Player player) {
//       Ability ability = playerDuck.getAbility();
 //       if (playerDuck.getFlightBoostCooldown() > 0) {
 //           player.sendSystemMessage(Component.translatable("Flight Boost on Cooldown: "+
 ////                   (int)Math.ceil(playerDuck.getFlightBoostCooldown()/20d) + " seconds left"));
//            return;
  //      }
     //   ItemStack firework = new ItemStack(Items.FIREWORK_ROCKET);
    //    FireworkRocketEntity fireworkRocketEntity = new FireworkRocketEntity(player.level(), firework, player);
    //    player.level().addFreshEntity(fireworkRocketEntity);
    //    int cooldown = 0;
   //     if (ability == Ability.Mari) cooldown = 30 * 20;
   //     else if (ability == Ability.Spriteboba) cooldown = 30 * 20;
     //   playerDuck.setFlightBoostCooldown(cooldown);
    }


    public static void toggleTrueInvis(Player player) {
        if (player.hasEffect(MobEffects.INVISIBILITY)) {
            player.removeEffect(MobEffects.INVISIBILITY);
        } else {
            player.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, -1, 0, false, false));
        }
    }

    public static void toggleBatForm(Player player) {
    //    LivingEntity identity = PlayerIdentity.getIdentity(player);
//        if (identity != null) {
    //        PlayerIdentity.updateIdentity((ServerPlayer) player, null, null);
  //      } else {
  ///          EntityType<Bat> batEntityType = EntityType.BAT;
  //          Bat bat = batEntityType.create(player.level());
  //          IdentityType<?> defaultType = IdentityType.from(bat);
   //         if (defaultType != null) {
           //     PlayerIdentity.updateIdentity((ServerPlayer) player, defaultType, bat);
  //          }
  //      }
    }

    public static void addAllIdentities(Player player) {
        for (EntityType<?> entityType : BuiltInRegistries.ENTITY_TYPE) {
            if (entityType != EntityType.ENDER_DRAGON) {
                Entity entity = entityType.create(player.level());
                if (entity instanceof LivingEntity living) {
  //                  IdentityType<?> defaultType = IdentityType.from(living);
   //                 if (defaultType != null) {
       //                 PlayerUnlocks.unlock((ServerPlayer) player, defaultType);
          //          }
                }
            }
        }
    }

    public static void removeAllIdentities(Player player) {
//        PlayerDataProvider provider = (PlayerDataProvider) player;
//        Set<IdentityType<?>> unlocked = new HashSet<>(provider.getUnlocked());//make a copy to avoid cc
//        for (IdentityType<?> identityType : unlocked) {
//            PlayerUnlocks.revoke((ServerPlayer) player, identityType);
    //    }
    }


    private static void equip(ServerPlayer source, ServerPlayer player, ResourceLocation identity, CompoundTag nbt) {
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
     /*       IdentityType<?> defaultType = IdentityType.from(living);
            if (defaultType != null) {
                boolean result = PlayerIdentity.updateIdentity(player, defaultType, (LivingEntity) created);
                if (result && IdentityConfig.getInstance().logCommands()) {
                    source.displayClientMessage(Component.translatable("identity.equip_success", Component.translatable(created.getType().getDescriptionId()), player.getDisplayName()), true);
                }
            }*/
        }

    }

    private static void unequip(ServerPlayer source, ServerPlayer player) {
    //    boolean result = PlayerIdentity.updateIdentity(player, null, null);
    //    if (result && IdentityConfig.getInstance().logCommands()) {
    //        source.displayClientMessage(Component.translatable("identity.unequip_success", player.getDisplayName()), false);
     //   }

    }


    public static boolean hasTrueInvis(Player player) {
        return player.hasEffect(MobEffects.INVISIBILITY);
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
        if (trueEntity instanceof Player player && died instanceof Player) {
            player.setAbsorptionAmount(player.getAbsorptionAmount() + 2);
            Constants.addStackableEffect(player, new MobEffectInstance(MobEffects.DAMAGE_BOOST, 20 * 60, 0, true, false));
            Constants.addStackableEffect(player, new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 20 * 60, 0, true, false));
        }


    }

    static final Predicate<ItemStack> KEEP = stack -> {
        return BuiltInRegistries.ITEM.getKey(stack.getItem()).equals(new ResourceLocation("immersive_melodies", "lute"));
    };

    void saveItemsMatching(Player player, Predicate<ItemStack> predicate) {
        NonNullList<ItemStack> keep = NonNullList.create();
        Inventory inv = player.getInventory();
        NonNullList<ItemStack> items = inv.items;
        for (int i = 0; i < items.size(); i++) {
            ItemStack stack = items.get(i);
            if (predicate.test(stack)) {
                keep.add(stack);
                items.set(i, ItemStack.EMPTY);
            }
        }

    }

    private void potionExpire(MobEffectEvent.Expired event) {
        LivingEntity living = event.getEntity();
        if (living instanceof Player player) {
            MobEffect mobEffect = event.getEffectInstance().getEffect();
            if (mobEffect == MobEffects.DAMAGE_BOOST || mobEffect == MobEffects.MOVEMENT_SPEED) {
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


    private void visibility(LivingEvent.LivingVisibilityEvent event) {
        LivingEntity living = event.getEntity();
        if (living instanceof Player player && hasTrueInvis(player)) {
            event.modifyVisibility(0);
        }
    }
}