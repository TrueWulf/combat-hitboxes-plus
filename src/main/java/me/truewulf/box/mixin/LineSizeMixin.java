//? if <1.21.11 {
/*
package me.truewulf.box.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import me.truewulf.box.Config;
import me.truewulf.box.Main;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RenderSystem.class)
public class LineSizeMixin {
    @Inject(method = "getShaderLineWidth", at = @At("HEAD"), cancellable = true, remap = false)
    private static void truewulf$lineWidth(CallbackInfoReturnable<Float> cir) {
        Config config = Config.getInstance();
        if (!config.enabled) {
            return;
        }
        cir.setReturnValue(Main.lineWidth);
    }
}
 *///?}
