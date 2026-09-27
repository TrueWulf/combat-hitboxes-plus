package me.truewulf.box;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.Projectile;

public final class ProjectileOwner {
    public enum Kind {
        OWN, ENEMY, UNKNOWN
    }

    private ProjectileOwner() {
    }

    public static Kind of(Projectile projectile) {
        Entity owner = projectile.getOwner();
        if (owner == null) {
            return Kind.UNKNOWN;
        }
        return owner == Main.mc.player ? Kind.OWN : Kind.ENEMY;
    }
}
