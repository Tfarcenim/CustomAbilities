package tfar.customabilities.entity;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import tfar.customabilities.ModParticleTypes;

public class BubbleEntity extends AbstractHurtingProjectile {

    private int duration = 60;

    private static final EntityDataAccessor<ParticleOptions> DATA_PARTICLE = SynchedEntityData.defineId(BubbleEntity.class, EntityDataSerializers.PARTICLE);

    public BubbleEntity(EntityType<? extends BubbleEntity> entityType, Level level) {
        super(entityType, level);
    }

    public BubbleEntity(EntityType<? extends AbstractHurtingProjectile> entityType, double x, double y, double z, double offsetX, double offsetY, double offsetZ, Level level) {
        super(entityType, x, y, z, offsetX, offsetY, offsetZ, level);
    }

    public BubbleEntity(EntityType<? extends AbstractHurtingProjectile> entityType, LivingEntity shooter, double offsetX, double offsetY, double offsetZ, Level level) {
        super(entityType, shooter, offsetX, offsetY, offsetZ, level);
    }

    @Override
    protected void defineSynchedData() {
        this.getEntityData().define(DATA_PARTICLE, ParticleTypes.ENTITY_EFFECT);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        Entity entity = result.getEntity();
        entity.setDeltaMovement(entity.getDeltaMovement().add(getDeltaMovement()));
        entity.hurtMarked = true;
    }

    @Override
    protected ParticleOptions getTrailParticle() {
        return ModParticleTypes.BUBBLE;
    }

    public ParticleOptions getParticle() {
        return this.getEntityData().get(DATA_PARTICLE);
    }

    public void setParticle(ParticleOptions particleOption) {
        this.getEntityData().set(DATA_PARTICLE, particleOption);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide) {

        } else {
            if (this.tickCount >= this.duration) {
                this.discard();
            }
        }
    }
}
