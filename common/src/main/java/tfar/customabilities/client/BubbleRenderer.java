package tfar.customabilities.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import tfar.customabilities.entity.BubbleEntity;


public class BubbleRenderer extends EntityRenderer<BubbleEntity> {


    protected BubbleRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(BubbleEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        super.render(entity, entityYaw, partialTick, poseStack, buffer, packedLight);
        //this.renderFlame(poseStack, buffer, entity);
     /*   Particle bubbleParticle = Minecraft.getInstance().particleEngine.createParticle(ModParticleTypes.BUBBLE,entity.getX(),entity.getY(),entity.getZ(),0,0,0);

        RenderSystem.setShader(GameRenderer::getParticleShader);
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferbuilder = tesselator.getBuilder();
        ParticleRenderType.PARTICLE_SHEET_OPAQUE.begin(bufferbuilder, Minecraft.getInstance().getTextureManager());

        bubbleParticle.render(bufferbuilder,Minecraft.getInstance().getEntityRenderDispatcher().camera, partialTick);

        ParticleRenderType.PARTICLE_SHEET_OPAQUE.end(tesselator);*/

    }

    @Override
    public ResourceLocation getTextureLocation(BubbleEntity entity) {
        return TextureAtlas.LOCATION_PARTICLES;
    }
}
