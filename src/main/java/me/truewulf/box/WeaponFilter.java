package me.truewulf.box;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.ItemStack;

public final class WeaponFilter {
    private WeaponFilter() {
    }

    public static boolean playerAllowed() {
        Config config = Config.getInstance();
        if (!config.weaponFilterEnabled) {
            return true;
        }
        LocalPlayer player = Main.mc.player;
        if (player == null) {
            return true;
        }
        if (matches(player.getMainHandItem(), config)) {
            return true;
        }
        return config.filterOffHand && matches(player.getOffhandItem(), config);
    }

    private static boolean matches(ItemStack stack, Config config) {
        if (stack.isEmpty()) {
            return false;
        }
        String id = idOf(stack);
        if (config.filterSword && id.contains("_sword")) {
            return true;
        }
        if (config.filterAxe && id.contains("_axe")) {
            return true;
        }
        if (config.filterPickaxe && id.contains("_pickaxe")) {
            return true;
        }
        if (config.filterShovel && id.contains("_shovel")) {
            return true;
        }
        if (config.filterMace && id.contains("mace")) {
            return true;
        }
        if (config.filterSpear && id.contains("_spear")) {
            return true;
        }
        if (config.filterTrident && id.equals("minecraft:trident")) {
            return true;
        }
        if (config.filterBow && id.equals("minecraft:bow")) {
            return true;
        }
        if (config.filterCrossbow && id.equals("minecraft:crossbow")) {
            return true;
        }
        if (config.filterEnderPearl && id.equals("minecraft:ender_pearl")) {
            return true;
        }
        if (config.filterWindCharge && id.contains("wind_charge")) {
            return true;
        }
        if (config.filterGoldenApple && id.contains("golden_apple")) {
            return true;
        }
        if (config.filterXpBottle && id.equals("minecraft:experience_bottle")) {
            return true;
        }
        if (config.filterTotem && id.equals("minecraft:totem_of_undying")) {
            return true;
        }
        if (config.filterFirework && id.equals("minecraft:firework_rocket")) {
            return true;
        }
        if (config.filterSplashPotion && id.equals("minecraft:splash_potion")) {
            return true;
        }
        if (config.filterGoldenCarrot && id.equals("minecraft:golden_carrot")) {
            return true;
        }
        if (config.filterElytra && id.equals("minecraft:elytra")) {
            return true;
        }
        if (config.filterEndCrystal && id.equals("minecraft:end_crystal")) {
            return true;
        }
        if (config.filterAnchor && id.equals("minecraft:respawn_anchor")) {
            return true;
        }
        if (config.filterGlowstone && id.equals("minecraft:glowstone")) {
            return true;
        }
        if (config.filterCobweb && id.equals("minecraft:cobweb")) {
            return true;
        }
        if (config.filterFishingRod && id.equals("minecraft:fishing_rod")) {
            return true;
        }
        if (config.filterSteak && id.equals("minecraft:cooked_beef")) {
            return true;
        }
        if (config.filterFlintAndSteel && id.equals("minecraft:flint_and_steel")) {
            return true;
        }
        if (config.filterArmorStand && id.equals("minecraft:armor_stand")) {
            return true;
        }
        if (config.filterLavaBucket && id.equals("minecraft:lava_bucket")) {
            return true;
        }
        if (config.filterWaterBucket && id.equals("minecraft:water_bucket")) {
            return true;
        }
        if (config.filterObsidian && id.equals("minecraft:obsidian")) {
            return true;
        }
        if (config.filterOther && (id.endsWith("_halberd") || id.endsWith("_katana")
                || id.endsWith("_rapier") || id.endsWith("_cutlass") || id.endsWith("_scythe")
                || id.endsWith("_dagger") || id.endsWith("_hammer") || id.endsWith("_glaive"))) {
            return true;
        }
        return false;
    }

    private static String idOf(ItemStack stack) {
        //? if >=1.21.11 {
        return stack.getItem().builtInRegistryHolder().key().identifier().toString();
        //?} else {
        /*return stack.getItem().builtInRegistryHolder().key().location().toString();
         *///?}
    }
}
