package tfar.customabilities;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingChangeTargetEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

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
        MinecraftForge.EVENT_BUS.addListener(this::damage);
        MinecraftForge.EVENT_BUS.addListener(this::heal);
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
                    player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST,40,0));
                    player.addEffect(new MobEffectInstance(MobEffects.HUNGER,40,0));
                    player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION,600,0));
                    player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED,40,0));
                    //-Full moon triggers mining fatigue and slowness
                } else if (player.level().getMoonBrightness() == 1) {
                    player.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 40, 0));
                    player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 0));
                }
            }

        }
    }

    private void damage(LivingDamageEvent event) {
        LivingEntity living = event.getEntity();
        if (living instanceof Player player) {
            if (Constants.hasAbility(player, Ability.Gar) && lessThan25PercentHealth(player)) {

            }
        }
    }

    private void heal(LivingHealEvent event) {
        LivingEntity living = event.getEntity();
        if (living instanceof Player player) {
            if (Constants.hasAbility(player, Ability.Gar) && lessThan25PercentHealth(player)) {

            }
        }
    }

    private static boolean lessThan25PercentHealth(LivingEntity player) {
        return player.getHealth() / player.getMaxHealth() < .25;
    }

    //this event is crap
    private void knockback(LivingKnockBackEvent e) {
    }

    private void commands(RegisterCommandsEvent e) {
        ModCommands.register(e.getDispatcher());
    }
}