package me.truewulf.box.mixin;

import me.truewulf.box.Config;
//? if <1.21.2 {
/*import net.minecraft.client.renderer.entity.layers.BeeStingerLayer;
import net.minecraft.world.entity.LivingEntity;
 *///?} elif <1.21.9 {
/*import net.minecraft.client.renderer.entity.layers.BeeStingerLayer;
import net.minecraft.client.renderer.entity.state.PlayerRenderState;
 *///?} else {
import net.minecraft.client.renderer.entity.layers.BeeStingerLayer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
//?}
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

//? if <1.21.2 {
/*@Mixin(BeeStingerLayer.class)
public class StuckStingerMixin {
    @Inject(method = "numStuck", at = @At("RETURN"), cancellable = true)
    private void truewulf$hookStingerCount(LivingEntity playerRenderState, CallbackInfoReturnable<Integer> cir) {
        if (Config.getInstance().hideArrow) {
            cir.setReturnValue(0);
        }
    }
}
 *///?} elif <1.21.9 {
/*@Mixin(BeeStingerLayer.class)
public class StuckStingerMixin {
    @Inject(method = "numStuck", at = @At("RETURN"), cancellable = true)
    private void truewulf$hookStingerCount(PlayerRenderState playerRenderState, CallbackInfoReturnable<Integer> cir) {
        if (Config.getInstance().hideArrow) {
            cir.setReturnValue(0);
        }
    }
}
 *///?} else {
@Mixin(BeeStingerLayer.class)
public class StuckStingerMixin {
    @Inject(method = "numStuck", at = @At("RETURN"), cancellable = true)
    private void truewulf$hookStingerCount(AvatarRenderState playerRenderState, CallbackInfoReturnable<Integer> cir) {
        if (Config.getInstance().hideArrow) {
            cir.setReturnValue(0);
        }
    }
}
//?}
