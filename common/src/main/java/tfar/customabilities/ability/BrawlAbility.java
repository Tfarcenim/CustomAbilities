package tfar.customabilities.ability;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import tfar.customabilities.entity.SmallTntEntity;
import tfar.customabilities.entity.SmokeCloudEntity;

//Brawl
//- Can eat raw meat without being affected by hunger (raw chicken, rotten flesh.) Can only eat meat, cannot eat bread, vegetables, berries, etc.
//        - Takes 75% less fall damage
//- Permanent Regeneration (Potency 1) (No particles)
//
//All toggles except Smoke Screen include explosion sound effect and particles
//
//"Explode" Keybind - Will immediately set off an explosion equal to 1 TNT block wherever Brawl is standing.
// Brawl does not get knocked back from the explosion and receives 18 points of damage.
// (Other players/mobs will receive the normal 39 hp damage TNT does) This keybind has a cooldown of 90 seconds
//
//"Bomb toss" Keybind toggle - Launches a bomb 5 blocks away that destroys a random amount of blocks within a 2x2x2 radius. Does 7 points of damage to players/mobs in that radius. This keybind has a cooldown of 5 seconds
//
//        (If possible within budget) "Sticky bomb" Keybind toggle - Can stick a bomb to a player/mob that is within 3 blocks.
//        (Crosshair has to be overtop of the player/mob to apply it) Explodes 10 seconds after toggling and will explode in the current location of the player/mob it was stuck to. destroys a random amount of blocks within a 2x2x2 radius. Does 7 points of damage to players/mobs in that radius. This keybind has a cooldown of 10 seconds.
//
//"Smoke Screen" Keybind toggle - Toggling this ability will place down campfire smoke in a 5x5x3 radius for 30 seconds. This keybind has a cooldown of 120 seconds.
//
//"Bomb Boost" Keybind toggle - Toggling this ability will launch Brawl 8 blocks in the direction that he is looking. Deals 7 hp worth of damage to Brawl.
public class BrawlAbility extends NewAbility{
    public BrawlAbility(String name) {
        super(name);
    }

    @Override
    public float modifyDamageTaken(LivingEntity target, DamageSource source, float amount) {
        if (source.is(DamageTypes.FALL)) {
            amount*=.25f;
        }
        return amount;
    }

    @Override
    public void handlePrimary(ServerPlayer player) {
        super.handlePrimary(player);
        Explosion explode = player.level().explode(player, player.getX(), player.getY(), player.getZ(), 4, Level.ExplosionInteraction.TNT);
        player.hurt(player.level().damageSources().explosion(explode),18);
        addCooldown(player,0,90 * 20);
    }

    @Override
    public void handleSecondary(ServerPlayer player) {
        super.handleSecondary(player);
        SmallTntEntity smallTntEntity = new SmallTntEntity(player.level(),player.getX(),player.getY()+player.getBbHeight()/2,player.getZ(),player);
        smallTntEntity.setFuse(20);
        smallTntEntity.setDeltaMovement(player.getLookAngle().scale(.75));
        player.level().addFreshEntity(smallTntEntity);
    }

    @Override
    public void handleTertiary(ServerPlayer player) {
        super.handleTertiary(player);
        SmokeCloudEntity smokeCloudEntity = new SmokeCloudEntity(player.level(),player.getX(),player.getY(),player.getZ());
        smokeCloudEntity.setParticle(ParticleTypes.CAMPFIRE_SIGNAL_SMOKE);
        smokeCloudEntity.setDuration(20*30);
        smokeCloudEntity.setHeight(3);
        smokeCloudEntity.setRadius(5);
        player.level().addFreshEntity(smokeCloudEntity);
        addCooldown(player,4,120*20);
    }

    @Override
    public void handleQuaternary(ServerPlayer player) {
        super.handleQuaternary(player);
        player.addDeltaMovement(player.getLookAngle());
        player.hurt(player.damageSources().explosion(player,player),7);

        player.serverLevel().playSound(null,player.blockPosition(),SoundEvents.GENERIC_EXPLODE,SoundSource.PLAYERS,1,1);


        player.serverLevel().sendParticles(ParticleTypes.EXPLOSION, player.getX(), player.getY(), player.getZ(),0, 1, 0, 0,0);

    }

    @Override
    public boolean canEat(ItemStack stack) {
        return stack.getItem().getFoodProperties().isMeat();
    }
}
