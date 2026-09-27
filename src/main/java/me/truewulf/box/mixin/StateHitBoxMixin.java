//? if >=1.21.5 && <1.21.11 {
/*
package me.truewulf.box.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import me.truewulf.box.EntityRenderStateAccessor;
import me.truewulf.box.LegacyRenderer;
import me.truewulf.box.Main;
//? if <1.21.9 {
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
//?} else {
import net.minecraft.client.renderer.SubmitNodeCollection;
import net.minecraft.client.renderer.feature.HitboxFeatureRenderer;
import org.joml.Matrix4f;
//?}
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
//? if <1.21.9 {
import net.minecraft.client.renderer.entity.state.HitboxesRenderState;
//?}
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//? if <1.21.9 {
@Mixin(EntityRenderDispatcher.class)
//?} else {
@Mixin(HitboxFeatureRenderer.class)
//?}
public abstract class StateHitBoxMixin {

    //? if <1.21.9 {
    @Inject(method = "renderHitboxes", at = @At("HEAD"), cancellable = true)
    private void truewulf$onDrawHitboxes(PoseStack poseStack, EntityRenderState state, HitboxesRenderState hitboxes, MultiBufferSource multiBufferSource, CallbackInfo ci) {
        Entity entity = ((EntityRenderStateAccessor) state).truewulf$getEntity();
        if (entity == null) {
            return;
        }
        VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.lines());
        if (LegacyRenderer.renderEntity(poseStack, vertexConsumer, entity, Float.NaN)) {
            ci.cancel();
        }
    }
    //?} else {
    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void truewulf$onRender(SubmitNodeCollection submitNodeCollection, net.minecraft.client.renderer.MultiBufferSource.BufferSource bufferSource, CallbackInfo ci) {
        if (submitNodeCollection.getHitboxSubmits().isEmpty()) {
            return;
        }
        boolean cancel = false;
        for (net.minecraft.client.renderer.SubmitNodeStorage.HitboxSubmit submit : submitNodeCollection.getHitboxSubmits()) {
            Entity entity = ((EntityRenderStateAccessor) submit.entityRenderState()).truewulf$getEntity();
            if (entity == null) {
                continue;
            }
            PoseStack poseStack = new PoseStack();
            poseStack.mulPose(submit.pose());
            VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.lines());
            if (LegacyRenderer.renderEntity(poseStack, vertexConsumer, entity, Float.NaN)) {
                cancel = true;
            }
        }
        if (cancel) {
            ci.cancel();
        }
    }
    //?}
}
 *///?}
