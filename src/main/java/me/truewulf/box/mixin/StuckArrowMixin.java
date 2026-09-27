package me.truewulf.box.mixin;

import me.truewulf.box.Config;
//? if <1.21.2 {
/*import net.minecraft.client.renderer.entity.layers.ArrowLayer;
import net.minecraft.world.entity.LivingEntity;
 *///?} elif <1.21.9 {
/*import net.minecraft.client.renderer.entity.layers.ArrowLayer;
import net.minecraft.client.renderer.entity.state.PlayerRenderState;
 *///?} else {
import net.minecraft.client.renderer.entity.layers.ArrowLayer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
//?}
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

//? if <1.21.2 {
/*@Mixin(ArrowLayer.class)
public class StuckArrowMixin {
    @Inject(method = "numStuck", at = @At("RETURN"), cancellable = true)
    private void truewulf$hookArrowCount(LivingEntity playerRenderState, CallbackInfoReturnable<Integer> cir) {
        if (Config.getInstance().hideArrow) {
            cir.setReturnValue(0);
        }
    }
}
 *///?} elif <1.21.9 {
/*@Mixin(ArrowLayer.class)
public class StuckArrowMixin {
    @Inject(method = "numStuck", at = @At("RETURN"), cancellable = true)
    private void truewulf$hookArrowCount(PlayerRenderState playerRenderState, CallbackInfoReturnable<Integer> cir) {
        if (Config.getInstance().hideArrow) {
            cir.setReturnValue(0);
        }
    }
}
 *///?} else {
@Mixin(ArrowLayer.class)
public class StuckArrowMixin {
    @Inject(method = "numStuck", at = @At("RETURN"), cancellable = true)
    private void truewulf$hookArrowCount(AvatarRenderState playerRenderState, CallbackInfoReturnable<Integer> cir) {
        if (Config.getInstance().hideArrow) {
            cir.setReturnValue(0);
        }
    }
}
//?}
