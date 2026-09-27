//? if >=1.21.11 {
package me.truewulf.box.mixin;

import me.truewulf.box.Config;
import me.truewulf.box.Main;
import me.truewulf.box.ProjectileOwner;
import me.truewulf.box.WeaponFilter;
import net.minecraft.client.renderer.debug.EntityHitboxDebugRenderer;
import net.minecraft.gizmos.GizmoStyle;
import net.minecraft.gizmos.Gizmos;
import net.minecraft.util.ARGB;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.enderdragon.EnderDragonPart;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrownEnderpearl;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityHitboxDebugRenderer.class)
public abstract class HitBoxRenderMixin {

    @Inject(method = "showHitboxes", at = @At("HEAD"), cancellable = true)
    private void truewulf$onDrawHitbox(Entity entity, float tickProgress, boolean inLocalServer, CallbackInfo ci) {
        if (inLocalServer) {
            return;
        }
        Config config = Config.getInstance();
        if (!config.enabled || Main.mc.player == null) {
            return;
        }
        ci.cancel();
        if (!WeaponFilter.playerAllowed()) {
            return;
        }
        if (config.hideFireworks && entity instanceof FireworkRocketEntity) {
            return;
        }
        if (entity instanceof Projectile projectile) {
            truewulf$renderProjectile(projectile, tickProgress, config);
            return;
        }
        boolean targeted = Main.mc.hitResult instanceof EntityHitResult hit && hit.getEntity() == entity;
        boolean inRange = entity.isAlive() && !entity.isSpectator()
                && Main.mc.player.isWithinEntityInteractionRange(entity, 1.1);
        if (!(entity instanceof LivingEntity living)) {
            truewulf$drawBox(entity, tickProgress, targeted && inRange ? config.targetBoxColor : config.hitBoxColor, config.line1);
            return;
        }

        boolean blocking = config.shieldColorEnabled
                && (!config.shieldOnlyTarget || targeted)
                && truewulf$isShieldBlocking(living);

        float lineWidth = Main.mc.player.distanceTo(entity) > config.distFor2 ? config.line2 : config.line1;

        int mainColor;
        if (blocking) {
            mainColor = config.shieldColor;
        } else if (targeted && inRange && living.hurtTime == 0) {
            mainColor = config.targetBoxColor;
        } else {
            mainColor = config.hitBoxColor;
        }

        truewulf$renderEntityBox(entity, tickProgress, lineWidth, mainColor, config);
    }

    @Unique
    private static void truewulf$renderProjectile(Projectile projectile, float tickProgress, Config config) {
        if (projectile instanceof ThrownEnderpearl) {
            if (!config.pearlsEnabled) {
                return;
            }
            ProjectileOwner.Kind kind = ProjectileOwner.of(projectile);
            if (kind == ProjectileOwner.Kind.OWN && !config.pearlOwn) {
                return;
            }
            if (kind == ProjectileOwner.Kind.ENEMY && !config.pearlOthers) {
                return;
            }
            if (kind == ProjectileOwner.Kind.UNKNOWN && !config.pearlUnknown) {
                return;
            }
            int color = switch (kind) {
                case OWN -> config.pearlOwnColor;
                case ENEMY -> config.pearlEnemyColor;
                case UNKNOWN -> config.pearlUnknownColor;
            };
            truewulf$drawBox(projectile, tickProgress, color, config.pearlLineWidth);
            return;
        }
        truewulf$drawBox(projectile, tickProgress, config.hitBoxColor, config.line1);
    }

    @Unique
    private static void truewulf$drawBox(Entity entity, float tickProgress, int color, float lineWidth) {
        Vec3 delta = entity.getPosition(tickProgress).subtract(entity.position());
        Gizmos.cuboid(entity.getBoundingBox().move(delta), GizmoStyle.stroke(color, lineWidth));
    }

    @Unique
    private static boolean truewulf$isShieldBlocking(LivingEntity living) {
        if (!living.isBlocking()) {
            return false;
        }
        ItemStack blockingWith = living.getItemBlockingWith();
        return !blockingWith.isEmpty() && blockingWith.is(Items.SHIELD);
    }

    @Unique
    private static void truewulf$renderEntityBox(Entity entity, float tickProgress, float lineWidth, int color, Config config) {
        Vec3 delta = entity.getPosition(tickProgress).subtract(entity.position());

        if (config.outlineEnabled) {
            Gizmos.cuboid(entity.getBoundingBox().move(delta), GizmoStyle.stroke(config.outlineColor, lineWidth * config.outlineMultiplier));
        }
        Gizmos.cuboid(entity.getBoundingBox().move(delta), GizmoStyle.stroke(color, lineWidth));
        Gizmos.point(entity.getPosition(tickProgress), color, 2.0F);

        Entity vehicle = entity.getVehicle();
        if (vehicle != null) {
            float halfWidth = Math.min(vehicle.getBbWidth(), entity.getBbWidth()) / 2.0F;
            Vec3 seat = vehicle.getPassengerRidingPosition(entity).add(delta);
            Gizmos.cuboid(new AABB(seat.x - halfWidth, seat.y, seat.z - halfWidth, seat.x + halfWidth, seat.y + 0.0625F, seat.z + halfWidth), GizmoStyle.stroke(-256, lineWidth));
        }

        if (config.renderEyeHeight) {
            AABB box = entity.getBoundingBox().move(delta);
            Gizmos.cuboid(
                    new AABB(box.minX, box.minY + entity.getEyeHeight() - 0.01F, box.minZ, box.maxX, box.minY + entity.getEyeHeight() + 0.01F, box.maxZ),
                    GizmoStyle.stroke(config.eyeColor, lineWidth)
            );
        }

        if (entity instanceof EnderDragon dragon) {
            for (EnderDragonPart part : dragon.getSubEntities()) {
                Vec3 partDelta = part.getPosition(tickProgress).subtract(part.position());
                Gizmos.cuboid(part.getBoundingBox().move(partDelta), GizmoStyle.stroke(ARGB.colorFromFloat(1.0F, 0.25F, 1.0F, 0.0F)));
            }
        }

        if (config.renderLookDir) {
            Vec3 eye = entity.getPosition(tickProgress).add(0.0, entity.getEyeHeight(), 0.0);
            Vec3 view = entity.getViewVector(tickProgress);
            if (config.lineLookDir) {
                Gizmos.line(eye, eye.add(view.scale(2.0)), config.lookColor, lineWidth);
            } else {
                Gizmos.arrow(eye, eye.add(view.scale(2.5)), config.lookColor, lineWidth * 1.5F);
            }
        }
    }
}

//?}
