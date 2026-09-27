//? if <1.21.11 {
/*
package me.truewulf.box;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
//? if <1.21.2 {
import net.minecraft.client.renderer.LevelRenderer;
//?} else {
import net.minecraft.client.renderer.ShapeRenderer;
//?}
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.EnderDragonPart;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrownEnderpearl;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public final class LegacyRenderer {
    private LegacyRenderer() {
    }

    public static boolean renderEntity(PoseStack poseStack, VertexConsumer vertexConsumer, Entity entity, float partialTickHint) {
        Config config = Config.getInstance();
        if (!config.enabled || Main.mc.player == null) {
            return false;
        }
        //? if <1.21.2 {
        float tickProgress = Main.mc.getTimer().getGameTimeDeltaPartialTick(false);
        //?} else {
        float tickProgress = Main.mc.getDeltaTracker().getGameTimeDeltaPartialTick(false);
        //?}
        if (!Float.isNaN(partialTickHint)) {
            tickProgress = partialTickHint;
        }
        if (!WeaponFilter.playerAllowed()) {
            return true;
        }

        if (config.hideFireworks && entity instanceof FireworkRocketEntity) {
            return true;
        }
        if (entity instanceof Projectile projectile) {
            drawProjectile(poseStack, vertexConsumer, projectile, config);
            return true;
        }
        boolean targeted = Main.mc.hitResult instanceof EntityHitResult hit && hit.getEntity() == entity;
        boolean inRange = entity.isAlive() && !entity.isSpectator()
                && Main.mc.player.canInteractWithEntity(entity, 1.1);
        if (!(entity instanceof LivingEntity living)) {
            Main.lineWidth = config.line1;
            drawBox(poseStack, vertexConsumer, relativeBox(entity), targeted && inRange ? config.targetBoxColor : config.hitBoxColor);
            flush();
            Main.lineWidth = Main.getVanillaWidth();
            return true;
        }

        boolean blocking = config.shieldColorEnabled
                && (!config.shieldOnlyTarget || targeted)
                && isShieldBlocking(living);

        float lineWidth = Main.mc.player.distanceTo(entity) > config.distFor2 ? config.line2 : config.line1;

        int mainColor;
        if (blocking) {
            mainColor = config.shieldColor;
        } else if (targeted && inRange && living.hurtTime == 0) {
            mainColor = config.targetBoxColor;
        } else {
            mainColor = config.hitBoxColor;
        }

        if (config.outlineEnabled) {
            Main.lineWidth = lineWidth * config.outlineMultiplier;
            drawBox(poseStack, vertexConsumer, entity.getBoundingBox(), config.outlineColor);
            flush();
        }
        Main.lineWidth = lineWidth;
        drawEntityBox(poseStack, vertexConsumer, entity, tickProgress, mainColor, config);
        flush();
        Main.lineWidth = Main.getVanillaWidth();
        return true;
    }

    private static void drawProjectile(PoseStack poseStack, VertexConsumer vertexConsumer, Projectile projectile, Config config) {
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
            Main.lineWidth = config.pearlLineWidth;
            drawBox(poseStack, vertexConsumer, relativeBox(projectile), color);
            flush();
            Main.lineWidth = Main.getVanillaWidth();
            return;
        }
        Main.lineWidth = config.line1;
        drawBox(poseStack, vertexConsumer, relativeBox(projectile), config.hitBoxColor);
        flush();
        Main.lineWidth = Main.getVanillaWidth();
    }

    private static AABB relativeBox(Entity entity) {
        return entity.getBoundingBox().move(-entity.getX(), -entity.getY(), -entity.getZ());
    }

    private static void drawEntityBox(PoseStack poseStack, VertexConsumer vertexConsumer, Entity entity, float tickProgress, int color, Config config) {
        AABB box = relativeBox(entity);
        drawBox(poseStack, vertexConsumer, box, color);

        if (config.renderEyeHeight) {
            float eye = entity.getEyeHeight();
            drawThinBox(poseStack, vertexConsumer,
                    box.minX, box.minY + eye - 0.01F, box.minZ,
                    box.maxX, box.minY + eye + 0.01F, box.maxZ,
                    config.eyeColor);
        }

        if (config.renderLookDir) {
            Vec3 eye = new Vec3(0.0, entity.getEyeHeight(), 0.0);
            Vec3 view = entity.getViewVector(tickProgress);
            Vec3 end = eye.add(view.scale(2.0));
            drawLine(poseStack, vertexConsumer, eye, end, config.lookColor);
            if (!config.lineLookDir) {
                Vec3 dir = view.normalize();
                Vec3 up = Math.abs(dir.y) < 0.99 ? new Vec3(0.0, 1.0, 0.0) : new Vec3(1.0, 0.0, 0.0);
                Vec3 side = dir.cross(up).normalize();
                Vec3 realUp = side.cross(dir).normalize();
                Vec3 back = dir.scale(-0.45);
                Vec3 sideOff = side.scale(0.22);
                Vec3 upOff = realUp.scale(0.22);
                drawLine(poseStack, vertexConsumer, end, end.add(back).add(sideOff), config.lookColor);
                drawLine(poseStack, vertexConsumer, end, end.add(back).subtract(sideOff), config.lookColor);
                drawLine(poseStack, vertexConsumer, end, end.add(back).add(upOff), config.lookColor);
                drawLine(poseStack, vertexConsumer, end, end.add(back).subtract(upOff), config.lookColor);
            }
        }

        Entity vehicle = entity.getVehicle();
        if (vehicle != null) {
            float halfWidth = Math.min(vehicle.getBbWidth(), entity.getBbWidth()) / 2.0F;
            Vec3 seat = vehicle.getPassengerRidingPosition(entity).subtract(entity.position());
            drawThinBox(poseStack, vertexConsumer,
                    seat.x - halfWidth, seat.y, seat.z - halfWidth,
                    seat.x + halfWidth, seat.y + 0.0625F, seat.z + halfWidth,
                    -256);
        }

        if (entity instanceof EnderDragon dragon) {
            for (EnderDragonPart part : dragon.getSubEntities()) {
                AABB partBox = part.getBoundingBox().move(-part.getX(), -part.getY(), -part.getZ());
                drawBox(poseStack, vertexConsumer, partBox, 0xFF40FF00);
            }
        }
    }

    private static boolean isShieldBlocking(LivingEntity living) {
        if (!living.isBlocking()) {
            return false;
        }
        //? if <1.21.2 {
        ItemStack stack = living.getUseItem();
        //?} else {
        ItemStack stack = living.getItemBlockingWith();
        //?}
        return !stack.isEmpty() && stack.is(Items.SHIELD);
    }

    private static void drawBox(PoseStack poseStack, VertexConsumer vertexConsumer, AABB aabb, int color) {
        float a = (color >>> 24 & 0xFF) / 255.0F;
        float r = (color >>> 16 & 0xFF) / 255.0F;
        float g = (color >>> 8 & 0xFF) / 255.0F;
        float b = (color & 0xFF) / 255.0F;
        //? if <1.21.2 {
        LevelRenderer.renderLineBox(poseStack, vertexConsumer, aabb, r, g, b, a);
        //?} elif <1.21.9 {
        ShapeRenderer.renderLineBox(poseStack, vertexConsumer, aabb, r, g, b, a);
        //?} else {
        ShapeRenderer.renderLineBox(poseStack.last(), vertexConsumer, aabb, r, g, b, a);
        //?}
    }

    private static void drawThinBox(PoseStack poseStack, VertexConsumer vertexConsumer, double x0, double y0, double z0, double x1, double y1, double z1, int color) {
        float a = (color >>> 24 & 0xFF) / 255.0F;
        float r = (color >>> 16 & 0xFF) / 255.0F;
        float g = (color >>> 8 & 0xFF) / 255.0F;
        float b = (color & 0xFF) / 255.0F;
        //? if <1.21.2 {
        LevelRenderer.renderLineBox(poseStack, vertexConsumer, x0, y0, z0, x1, y1, z1, r, g, b, a);
        //?} elif <1.21.9 {
        ShapeRenderer.renderLineBox(poseStack, vertexConsumer, x0, y0, z0, x1, y1, z1, r, g, b, a);
        //?} else {
        ShapeRenderer.renderLineBox(poseStack.last(), vertexConsumer, x0, y0, z0, x1, y1, z1, r, g, b, a);
        //?}
    }

    private static void drawLine(PoseStack poseStack, VertexConsumer vertexConsumer, Vec3 start, Vec3 end, int color) {
        double dx = end.x - start.x;
        double dy = end.y - start.y;
        double dz = end.z - start.z;
        double len = Math.sqrt(dx * dx + dy * dy + dz * dz);
        if (len < 1.0E-5D) {
            return;
        }
        float nx = (float) (dx / len);
        float ny = (float) (dy / len);
        float nz = (float) (dz / len);
        PoseStack.Pose pose = poseStack.last();
        vertexConsumer.addVertex(pose, (float) start.x, (float) start.y, (float) start.z)
                .setColor(color)
                .setNormal(pose, nx, ny, nz);
        vertexConsumer.addVertex(pose, (float) end.x, (float) end.y, (float) end.z)
                .setColor(color)
                .setNormal(pose, nx, ny, nz);
    }

    private static void flush() {
        Main.mc.renderBuffers().bufferSource().endBatch(RenderType.lines());
    }
}
 *///?}
