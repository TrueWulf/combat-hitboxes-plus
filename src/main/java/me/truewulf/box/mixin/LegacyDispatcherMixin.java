//? if <1.21.8 {
/*
package me.truewulf.box.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import me.truewulf.box.LegacyRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderDispatcher.class)
public abstract class LegacyDispatcherMixin {
    @Inject(method = "renderHitbox", at = @At("HEAD"), cancellable = true)
    private static void truewulf$onDrawHitbox(PoseStack poseStack, VertexConsumer vertexConsumer, Entity entity, float partialTick, float r, float g, float b, CallbackInfo ci) {
        if (LegacyRenderer.renderEntity(poseStack, vertexConsumer, entity, partialTick)) {
            ci.cancel();
        }
    }
}
 *///?}
