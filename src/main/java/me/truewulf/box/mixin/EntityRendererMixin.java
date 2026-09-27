//? if >=1.21.5 && <1.21.11 {
/*
package me.truewulf.box.mixin;

import me.truewulf.box.EntityRenderStateAccessor;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public class EntityRendererMixin {
    @Inject(method = "extractRenderState", at = @At("HEAD"))
    private void truewulf$captureEntity(Entity entity, EntityRenderState state, float tickProgress, CallbackInfo ci) {
        ((EntityRenderStateAccessor) state).truewulf$setEntity(entity);
    }
}
 *///?}
