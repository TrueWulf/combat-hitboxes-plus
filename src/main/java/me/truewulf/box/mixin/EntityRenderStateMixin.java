//? if >=1.21.5 && <1.21.11 {
/*
package me.truewulf.box.mixin;

import me.truewulf.box.EntityRenderStateAccessor;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(EntityRenderState.class)
public class EntityRenderStateMixin implements EntityRenderStateAccessor {
    @Unique
    private Entity truewulf$entity;

    @Override
    public void truewulf$setEntity(Entity entity) {
        this.truewulf$entity = entity;
    }

    @Override
    public Entity truewulf$getEntity() {
        return this.truewulf$entity;
    }
}
 *///?}
