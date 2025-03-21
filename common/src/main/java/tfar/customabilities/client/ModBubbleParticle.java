package tfar.customabilities.client;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;

public class ModBubbleParticle  extends TextureSheetParticle {
    ModBubbleParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        super(level, x, y, z);
        this.setSize(0.02F, 0.02F);
        this.quadSize *= this.random.nextFloat() * 0.6F + 0.2F;
        this.xd = xSpeed * 0.2 + (Math.random() * 2.0D - 1.0D) * 0.02;
        this.yd = ySpeed * 0.2 + (Math.random() * 2.0D - 1.0D) * 0.02;
        this.zd = zSpeed * 0.2 + (Math.random() * 2.0D - 1.0D) * 0.02;
        this.lifetime = (int)(8.0D / (Math.random() * 0.8D + 0.2D));
    }

    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.lifetime-- <= 0) {
            this.remove();
        } else {
            this.yd += 0.002D;
            this.move(this.xd, this.yd, this.zd);
            this.xd *= 0.85F;
            this.yd *= 0.85F;
            this.zd *= 0.85F;
        }
    }

    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprite;

        public Provider(SpriteSet p_105793_) {
            this.sprite = p_105793_;
        }

        public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double p_105810_, double p_105811_) {
            ModBubbleParticle bubbleparticle = new ModBubbleParticle(level, x, y, z, xSpeed, p_105810_, p_105811_);
            bubbleparticle.pickSprite(this.sprite);
            return bubbleparticle;
        }
    }
}
