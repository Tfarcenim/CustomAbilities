package tfar.customabilities.entity;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.logging.LogUtils;

import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.commands.arguments.ParticleArgument;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.PushReaction;
import org.slf4j.Logger;
import tfar.customabilities.init.ModEntityTypes;

public class SmokeCloudEntity extends Entity implements TraceableEntity {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final int TIME_BETWEEN_APPLICATIONS = 5;
    private static final EntityDataAccessor<Float> DATA_RADIUS = SynchedEntityData.defineId(SmokeCloudEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<ParticleOptions> DATA_PARTICLE = SynchedEntityData.defineId(SmokeCloudEntity.class, EntityDataSerializers.PARTICLE);
    private static final float MAX_RADIUS = 32.0F;
    private static final float MINIMAL_RADIUS = 0.5F;
    private static final float DEFAULT_RADIUS = 3.0F;
    public static final float DEFAULT_WIDTH = 6.0F;
    public static final float HEIGHT = 0.5F;
    private int duration = 600;
    private int waitTime = 20;
    private int reapplicationDelay = 20;
    private int durationOnUse;
    private float radiusOnUse;
    private float radiusPerTick;
    @Nullable
    private LivingEntity owner;
    @Nullable
    private UUID ownerUUID;

    public SmokeCloudEntity(EntityType<? extends SmokeCloudEntity> entityType, Level level) {
        super(entityType, level);
        this.noPhysics = true;
    }

    public SmokeCloudEntity(Level level, double x, double y, double z) {
        this(ModEntityTypes.SMOKE_CLOUD, level);
        this.setPos(x, y, z);
    }

    @Override
    protected void defineSynchedData() {
        this.getEntityData().define(DATA_RADIUS, 3.0F);
        this.getEntityData().define(DATA_PARTICLE, ParticleTypes.ENTITY_EFFECT);
    }

    public void setRadius(float radius) {
        if (!this.level().isClientSide) {
            this.getEntityData().set(DATA_RADIUS, Mth.clamp(radius, 0.0F, 32.0F));
        }

    }

    @Override
    public void refreshDimensions() {
        double d0 = this.getX();
        double d1 = this.getY();
        double d2 = this.getZ();
        super.refreshDimensions();
        this.setPos(d0, d1, d2);
    }

    public float getRadius() {
        return this.getEntityData().get(DATA_RADIUS);
    }

    public ParticleOptions getParticle() {
        return this.getEntityData().get(DATA_PARTICLE);
    }

    public void setParticle(ParticleOptions particleOption) {
        this.getEntityData().set(DATA_PARTICLE, particleOption);
    }

    public int getDuration() {
        return this.duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public void tick() {
        super.tick();
        float radius = this.getRadius();
        if (this.level().isClientSide) {

            ParticleOptions particleoptions = this.getParticle();
            int i = Mth.ceil((float) Math.PI * radius * radius);

            for(int j = 0; j < i; ++j) {
                float angle = this.random.nextFloat() * ((float)Math.PI * 2F);
                float r = Mth.sqrt(this.random.nextFloat()) * radius;
                double xPos = this.getX() + (double)(Mth.cos(angle) * r);
                double yPos = this.getY() + random.nextDouble() * 2;
                double d4 = this.getZ() + (double)(Mth.sin(angle) * r);
                double xSpeed = 0 * (0.5D - this.random.nextDouble()) * 0.15D;
                double ySpeed = 0.01F;
                double zSpeed = 0 *(0.5D - this.random.nextDouble()) * 0.15D;

                this.level().addAlwaysVisibleParticle(particleoptions, xPos, yPos, d4, xSpeed, ySpeed, zSpeed);
            }
        } else {
            if (this.tickCount >= this.waitTime + this.duration) {
                this.discard();
                return;
            }

            boolean flag1 = this.tickCount < this.waitTime;

            if (flag1) {
                return;
            }

            if (this.radiusPerTick != 0.0F) {
                radius += this.radiusPerTick;
                if (radius < 0.5F) {
                    this.discard();
                    return;
                }

                this.setRadius(radius);
            }
        }
    }

    public float getRadiusOnUse() {
        return this.radiusOnUse;
    }

    public void setRadiusOnUse(float radiusOnUse) {
        this.radiusOnUse = radiusOnUse;
    }

    public float getRadiusPerTick() {
        return this.radiusPerTick;
    }

    public void setRadiusPerTick(float radiusPerTick) {
        this.radiusPerTick = radiusPerTick;
    }

    public int getDurationOnUse() {
        return this.durationOnUse;
    }

    public void setDurationOnUse(int durationOnUse) {
        this.durationOnUse = durationOnUse;
    }

    public int getWaitTime() {
        return this.waitTime;
    }

    public void setWaitTime(int waitTime) {
        this.waitTime = waitTime;
    }

    public void setOwner(@Nullable LivingEntity owner) {
        this.owner = owner;
        this.ownerUUID = owner == null ? null : owner.getUUID();
    }

    @Override
    @Nullable
    public LivingEntity getOwner() {
        if (this.owner == null && this.ownerUUID != null && this.level() instanceof ServerLevel) {
            Entity entity = ((ServerLevel)this.level()).getEntity(this.ownerUUID);
            if (entity instanceof LivingEntity) {
                this.owner = (LivingEntity)entity;
            }
        }

        return this.owner;
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {
        this.tickCount = compound.getInt("Age");
        this.duration = compound.getInt("Duration");
        this.waitTime = compound.getInt("WaitTime");
        this.reapplicationDelay = compound.getInt("ReapplicationDelay");
        this.durationOnUse = compound.getInt("DurationOnUse");
        this.radiusOnUse = compound.getFloat("RadiusOnUse");
        this.radiusPerTick = compound.getFloat("RadiusPerTick");
        this.setRadius(compound.getFloat("Radius"));
        if (compound.hasUUID("Owner")) {
            this.ownerUUID = compound.getUUID("Owner");
        }

        if (compound.contains("Particle", 8)) {
            try {
                this.setParticle(ParticleArgument.readParticle(new StringReader(compound.getString("Particle")), BuiltInRegistries.PARTICLE_TYPE.asLookup()));
            } catch (CommandSyntaxException commandsyntaxexception) {
                LOGGER.warn("Couldn't load custom particle {}", compound.getString("Particle"), commandsyntaxexception);
            }
        }
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
        compound.putInt("Age", this.tickCount);
        compound.putInt("Duration", this.duration);
        compound.putInt("WaitTime", this.waitTime);
        compound.putInt("ReapplicationDelay", this.reapplicationDelay);
        compound.putInt("DurationOnUse", this.durationOnUse);
        compound.putFloat("RadiusOnUse", this.radiusOnUse);
        compound.putFloat("RadiusPerTick", this.radiusPerTick);
        compound.putFloat("Radius", this.getRadius());
        compound.putString("Particle", this.getParticle().writeToString());
        if (this.ownerUUID != null) {
            compound.putUUID("Owner", this.ownerUUID);
        }
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        if (DATA_RADIUS.equals(key)) {
            this.refreshDimensions();
        }

        super.onSyncedDataUpdated(key);
    }

    @Override
    public PushReaction getPistonPushReaction() {
        return PushReaction.IGNORE;
    }

    @Override
    public EntityDimensions getDimensions(Pose pose) {
        return EntityDimensions.scalable(this.getRadius() * 2.0F, 3);
    }
}
