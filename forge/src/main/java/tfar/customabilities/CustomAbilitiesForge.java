package tfar.customabilities;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
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

import java.util.List;

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
        MinecraftForge.EVENT_BUS.addListener(this::targetPlayer);
        MinecraftForge.EVENT_BUS.addListener(this::knockback);
        MinecraftForge.EVENT_BUS.addListener(this::playertick);
        //MinecraftForge.EVENT_BUS.addListener(this::damage);
        //MinecraftForge.EVENT_BUS.addListener(this::heal);
        MinecraftForge.EVENT_BUS.addListener(this::clonePlayer);
        MinecraftForge.EVENT_BUS.addListener(this::sleepInBed);
        MinecraftForge.EVENT_BUS.addListener(this::onKill);
        MinecraftForge.EVENT_BUS.addListener(this::visibility);
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(ModDatagen::start);
        bus.addListener(this::setup);
        if (FMLEnvironment.dist.isClient()) {
            bus.addListener(this::clientSetup);
            bus.addListener(Client::registerKeybinds);
        }
    }

    private void setup(FMLCommonSetupEvent e) {
        PacketHandler.registerMessages();
    }



    private void clientSetup(FMLClientSetupEvent e) {
        Client.setupClient();
    }

    private void targetPlayer(LivingChangeTargetEvent e) {
        LivingEntity originalTarget = e.getOriginalTarget();
        LivingEntity attacker = e.getEntity();
        if (originalTarget instanceof Player player && attacker instanceof Warden warden) {
            Ability ability = ((PlayerDuck) player).getAbility();
            if (ability == Ability.Syd) {
                e.setCanceled(true);//wardens don't attack this ability
            }
        }
    }


    private void playertick(TickEvent.PlayerTickEvent event) {
        if (event.side == LogicalSide.SERVER && event.phase == TickEvent.Phase.START) {
            Player player = event.player;
            CompoundTag tag = player.getPersistentData();
            if (player.isCrouching()) {
                int crouchTimer = tag.getInt("crouchTimer");
                tag.putInt("crouchTimer", crouchTimer + 1);
            } else {
                tag.remove("crouchTimer");
            }

            if (Constants.hasAbility(player,Ability.Miblex) && !player.level().isDay() &&player.level().getGameTime() % 20 == 0) {
                //-New moon triggers strength, hunger, night vision and speed
                if (player.level().getMoonBrightness() == 0) {
                    player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST,40,0,true,true));
                    player.addEffect(new MobEffectInstance(MobEffects.HUNGER,40,0,true,true));
                    player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION,600,0,true,true));
                    player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED,40,0,true,true));
                    //-Full moon triggers mining fatigue and slowness
                } else if (player.level().getMoonBrightness() == 1) {
                    player.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 40, 0,true,true));
                    player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 0,true,true));
                }
            }

            if (Constants.hasAbility(player,Ability.Gar) && lessThan25PercentHealth(player)) {
                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 40, 0, true, true));
            }
            if (Constants.hasAbility(player,Ability.Spriteboba)) {
                BlockState feetBlockState = player.getBlockStateOn();
                if (feetBlockState != null && feetBlockState.getLightEmission() > 0) {
                    player.addEffect(new MobEffectInstance(MobEffects.REGENERATION,40,0,true,true));
                }
            }
        }
    }

    private void damage(LivingDamageEvent event) {
        LivingEntity living = event.getEntity();
        if (living instanceof Player player) {
            if (Constants.hasAbility(player, Ability.Gar)) {
                if (lessThan25PercentHealth(player)) {
                }
            }
        }
    }

    private void heal(LivingHealEvent event) {
        LivingEntity living = event.getEntity();
        if (living instanceof Player player) {
            if (Constants.hasAbility(player, Ability.Gar)) {
                if (lessThan25PercentHealth(player)) {

                }
            }
        }
    }

    private void clonePlayer(PlayerEvent.Clone event) {
        Player original = event.getOriginal();
        Player player = event.getEntity();
        ((PlayerDuck)player).setAbility(((PlayerDuck)original).getAbility());
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

    private static boolean lessThan25PercentHealth(LivingEntity player) {
        return player.getHealth() / player.getMaxHealth() < .25;
    }

    public static void toggleTrueInvis(Player player) {
        CompoundTag tag = player.getPersistentData();
        boolean invis = tag.getBoolean("invis");
        tag.putBoolean("invis",!invis);
        player.setInvisible(!invis);
    }

    public static boolean hasTrueInvis(Player player) {
        return Constants.hasAbility(player,Ability.Ramsey) && player.getPersistentData().getBoolean("invis");
    }

    //this event is crap
    private void knockback(LivingKnockBackEvent e) {
    }

    private void commands(RegisterCommandsEvent e) {
        ModCommands.register(e.getDispatcher());
    }

    private void onKill(LivingDeathEvent event) {
        Entity trueEntity = event.getSource().getEntity();
        if (trueEntity instanceof Player player&& Constants.hasAbility(player,Ability.Ramsey)) {
            player.setAbsorptionAmount(player.getAbsorptionAmount()+2);
        }
    }

    private void visibility(LivingEvent.LivingVisibilityEvent event) {
        LivingEntity living = event.getEntity();
        if (living instanceof Player player && hasTrueInvis(player)) {
            event.modifyVisibility(0);
        }
    }
}