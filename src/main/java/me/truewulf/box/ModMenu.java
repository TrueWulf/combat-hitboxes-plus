package me.truewulf.box;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

public class ModMenu implements ModMenuApi {
    private static Component t(String key) {
        return Component.translatable("combat_hitboxes." + key);
    }

    private static Component title() {
        return Component.translatable("combat_hitboxes.title")
                .append(Component.literal("+").withStyle(ChatFormatting.GREEN));
    }

    private static Component section(String key) {
        return Component.translatable("combat_hitboxes.section." + key);
    }

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> {
            Config config = Config.getInstance();

            ConfigBuilder builder = ConfigBuilder.create()
                    .setParentScreen(parent)
                    .setTitle(title())
                    .setSavingRunnable(config::save);

            ConfigEntryBuilder entry = builder.entryBuilder();

            ConfigCategory general = builder.getOrCreateCategory(t("category.general"));
            general.addEntry(entry.startBooleanToggle(t("general.enabled"), config.enabled)
                    .setDefaultValue(true)
                    .setSaveConsumer(value -> config.enabled = value)
                    .build());
            general.addEntry(entry.startTextDescription(section("states")).build());
            general.addEntry(entry.startBooleanToggle(t("general.shield_color"), config.shieldColorEnabled)
                    .setDefaultValue(true)
                    .setTooltip(t("general.shield_color.tooltip"))
                    .setSaveConsumer(value -> config.shieldColorEnabled = value)
                    .build());
            general.addEntry(entry.startBooleanToggle(t("general.shield_only_target"), config.shieldOnlyTarget)
                    .setDefaultValue(false)
                    .setSaveConsumer(value -> config.shieldOnlyTarget = value)
                    .build());
            general.addEntry(entry.startTextDescription(section("rendering")).build());
            general.addEntry(entry.startBooleanToggle(t("general.render_eye_height"), config.renderEyeHeight)
                    .setDefaultValue(true)
                    .setSaveConsumer(value -> config.renderEyeHeight = value)
                    .build());
            general.addEntry(entry.startBooleanToggle(t("general.render_look_dir"), config.renderLookDir)
                    .setDefaultValue(true)
                    .setSaveConsumer(value -> config.renderLookDir = value)
                    .build());
            general.addEntry(entry.startBooleanToggle(t("general.look_as_line"), config.lineLookDir)
                    .setDefaultValue(true)
                    .setTooltip(t("general.look_as_line.tooltip"))
                    .setSaveConsumer(value -> config.lineLookDir = value)
                    .build());
            general.addEntry(entry.startBooleanToggle(t("general.hide_fireworks"), config.hideFireworks)
                    .setDefaultValue(true)
                    .setSaveConsumer(value -> config.hideFireworks = value)
                    .build());
            general.addEntry(entry.startBooleanToggle(t("general.hide_stuck_arrows"), config.hideArrow)
                    .setDefaultValue(false)
                    .setTooltip(t("general.hide_stuck_arrows.tooltip"))
                    .setSaveConsumer(value -> config.hideArrow = value)
                    .build());

            ConfigCategory colors = builder.getOrCreateCategory(t("category.colors"));
            colors.addEntry(entry.startTextDescription(section("colors")).build());
            colors.addEntry(entry.startAlphaColorField(t("colors.base"), config.hitBoxColor)
                    .setDefaultValue(0xFFFFFFFF)
                    .setSaveConsumer(value -> config.hitBoxColor = value)
                    .build());
            colors.addEntry(entry.startAlphaColorField(t("colors.target_box"), config.targetBoxColor)
                    .setDefaultValue(0xFFFF0000)
                    .setTooltip(t("colors.target_box.tooltip"))
                    .setSaveConsumer(value -> config.targetBoxColor = value)
                    .build());
            colors.addEntry(entry.startAlphaColorField(t("colors.shield"), config.shieldColor)
                    .setDefaultValue(0xFF9B30FF)
                    .setSaveConsumer(value -> config.shieldColor = value)
                    .build());
            colors.addEntry(entry.startAlphaColorField(t("colors.eye"), config.eyeColor)
                    .setDefaultValue(0xFFFF0000)
                    .setSaveConsumer(value -> config.eyeColor = value)
                    .build());
            colors.addEntry(entry.startAlphaColorField(t("colors.look"), config.lookColor)
                    .setDefaultValue(0xFF0000FF)
                    .setSaveConsumer(value -> config.lookColor = value)
                    .build());
            colors.addEntry(entry.startTextDescription(section("outline")).build());
            colors.addEntry(entry.startBooleanToggle(t("colors.outline_enabled"), config.outlineEnabled)
                    .setDefaultValue(false)
                    .setSaveConsumer(value -> config.outlineEnabled = value)
                    .build());
            colors.addEntry(entry.startAlphaColorField(t("colors.outline_color"), config.outlineColor)
                    .setDefaultValue(0xFF000000)
                    .setSaveConsumer(value -> config.outlineColor = value)
                    .build());
            colors.addEntry(entry.startFloatField(t("colors.outline_multiplier"), config.outlineMultiplier)
                    .setMin(Math.nextUp(1.0F)).setMax(10.0F)
                    .setDefaultValue(2.0F)
                    .setSaveConsumer(value -> config.outlineMultiplier = value)
                    .build());
            colors.addEntry(entry.startTextDescription(section("width")).build());
            colors.addEntry(entry.startFloatField(t("colors.line1"), config.line1)
                    .setMin(0.0F).setMax(25.0F)
                    .setDefaultValue(2.5F)
                    .setSaveConsumer(value -> config.line1 = value)
                    .build());
            colors.addEntry(entry.startDoubleField(t("colors.dist_for2"), config.distFor2)
                    .setMin(0).setMax(256)
                    .setDefaultValue(32.0)
                    .setTooltip(t("colors.dist_for2.tooltip"))
                    .setSaveConsumer(value -> config.distFor2 = value)
                    .build());
            colors.addEntry(entry.startFloatField(t("colors.line2"), config.line2)
                    .setMin(0.0F).setMax(25.0F)
                    .setDefaultValue(2.5F)
                    .setSaveConsumer(value -> config.line2 = value)
                    .build());

            ConfigCategory projectiles = builder.getOrCreateCategory(t("category.projectiles"));
            projectiles.addEntry(entry.startTextDescription(section("pearls")).build());
            projectiles.addEntry(entry.startBooleanToggle(t("projectiles.pearls"), config.pearlsEnabled)
                    .setDefaultValue(true)
                    .setSaveConsumer(value -> config.pearlsEnabled = value)
                    .build());
            projectiles.addEntry(entry.startBooleanToggle(t("projectiles.pearl_own"), config.pearlOwn)
                    .setDefaultValue(true)
                    .setSaveConsumer(value -> config.pearlOwn = value)
                    .build());
            projectiles.addEntry(entry.startBooleanToggle(t("projectiles.pearl_others"), config.pearlOthers)
                    .setDefaultValue(true)
                    .setSaveConsumer(value -> config.pearlOthers = value)
                    .build());
            projectiles.addEntry(entry.startBooleanToggle(t("projectiles.pearl_unknown"), config.pearlUnknown)
                    .setDefaultValue(true)
                    .setSaveConsumer(value -> config.pearlUnknown = value)
                    .build());
            projectiles.addEntry(entry.startAlphaColorField(t("projectiles.pearl_own_color"), config.pearlOwnColor)
                    .setDefaultValue(0xFF0055FF)
                    .setSaveConsumer(value -> config.pearlOwnColor = value)
                    .build());
            projectiles.addEntry(entry.startAlphaColorField(t("projectiles.pearl_enemy_color"), config.pearlEnemyColor)
                    .setDefaultValue(0xFFFF3333)
                    .setSaveConsumer(value -> config.pearlEnemyColor = value)
                    .build());
            projectiles.addEntry(entry.startAlphaColorField(t("projectiles.pearl_unknown_color"), config.pearlUnknownColor)
                    .setDefaultValue(0xFF3355CC)
                    .setSaveConsumer(value -> config.pearlUnknownColor = value)
                    .build());
            projectiles.addEntry(entry.startFloatField(t("projectiles.pearl_width"), config.pearlLineWidth)
                    .setMin(0.0F).setMax(25.0F)
                    .setDefaultValue(2.5F)
                    .setSaveConsumer(value -> config.pearlLineWidth = value)
                    .build());

            ConfigCategory filter = builder.getOrCreateCategory(t("category.filter"));
            filter.addEntry(entry.startBooleanToggle(t("filter.enabled"), config.weaponFilterEnabled)
                    .setDefaultValue(false)
                    .setTooltip(t("filter.enabled.tooltip"))
                    .setSaveConsumer(value -> config.weaponFilterEnabled = value)
                    .build());
            filter.addEntry(entry.startBooleanToggle(t("filter.offhand"), config.filterOffHand)
                    .setDefaultValue(true)
                    .setSaveConsumer(value -> config.filterOffHand = value)
                    .build());
            filter.addEntry(entry.startTextDescription(section("melee")).build());
            filter.addEntry(entry.startBooleanToggle(t("filter.sword"), config.filterSword)
                    .setDefaultValue(true)
                    .setSaveConsumer(value -> config.filterSword = value)
                    .build());
            filter.addEntry(entry.startBooleanToggle(t("filter.axe"), config.filterAxe)
                    .setDefaultValue(true)
                    .setSaveConsumer(value -> config.filterAxe = value)
                    .build());
            filter.addEntry(entry.startBooleanToggle(t("filter.pickaxe"), config.filterPickaxe)
                    .setDefaultValue(false)
                    .setSaveConsumer(value -> config.filterPickaxe = value)
                    .build());
            filter.addEntry(entry.startBooleanToggle(t("filter.shovel"), config.filterShovel)
                    .setDefaultValue(false)
                    .setSaveConsumer(value -> config.filterShovel = value)
                    .build());
            filter.addEntry(entry.startBooleanToggle(t("filter.mace"), config.filterMace)
                    .setDefaultValue(true)
                    .setSaveConsumer(value -> config.filterMace = value)
                    .build());
            filter.addEntry(entry.startBooleanToggle(t("filter.trident"), config.filterTrident)
                    .setDefaultValue(true)
                    .setSaveConsumer(value -> config.filterTrident = value)
                    .build());
            filter.addEntry(entry.startBooleanToggle(t("filter.spear"), config.filterSpear)
                    .setDefaultValue(true)
                    .setSaveConsumer(value -> config.filterSpear = value)
                    .build());
            filter.addEntry(entry.startTextDescription(section("ranged")).build());
            filter.addEntry(entry.startBooleanToggle(t("filter.bow"), config.filterBow)
                    .setDefaultValue(true)
                    .setSaveConsumer(value -> config.filterBow = value)
                    .build());
            filter.addEntry(entry.startBooleanToggle(t("filter.crossbow"), config.filterCrossbow)
                    .setDefaultValue(true)
                    .setSaveConsumer(value -> config.filterCrossbow = value)
                    .build());
            filter.addEntry(entry.startTextDescription(section("items")).build());
            filter.addEntry(entry.startBooleanToggle(t("filter.ender_pearl"), config.filterEnderPearl)
                    .setDefaultValue(true)
                    .setSaveConsumer(value -> config.filterEnderPearl = value)
                    .build());
            filter.addEntry(entry.startBooleanToggle(t("filter.wind_charge"), config.filterWindCharge)
                    .setDefaultValue(true)
                    .setSaveConsumer(value -> config.filterWindCharge = value)
                    .build());
            filter.addEntry(entry.startBooleanToggle(t("filter.golden_apple"), config.filterGoldenApple)
                    .setDefaultValue(true)
                    .setSaveConsumer(value -> config.filterGoldenApple = value)
                    .build());
            filter.addEntry(entry.startBooleanToggle(t("filter.golden_carrot"), config.filterGoldenCarrot)
                    .setDefaultValue(false)
                    .setSaveConsumer(value -> config.filterGoldenCarrot = value)
                    .build());
            filter.addEntry(entry.startBooleanToggle(t("filter.xp_bottle"), config.filterXpBottle)
                    .setDefaultValue(false)
                    .setSaveConsumer(value -> config.filterXpBottle = value)
                    .build());
            filter.addEntry(entry.startBooleanToggle(t("filter.totem"), config.filterTotem)
                    .setDefaultValue(true)
                    .setSaveConsumer(value -> config.filterTotem = value)
                    .build());
            filter.addEntry(entry.startBooleanToggle(t("filter.firework"), config.filterFirework)
                    .setDefaultValue(false)
                    .setSaveConsumer(value -> config.filterFirework = value)
                    .build());
            filter.addEntry(entry.startBooleanToggle(t("filter.splash_potion"), config.filterSplashPotion)
                    .setDefaultValue(false)
                    .setSaveConsumer(value -> config.filterSplashPotion = value)
                    .build());
            filter.addEntry(entry.startBooleanToggle(t("filter.elytra"), config.filterElytra)
                    .setDefaultValue(true)
                    .setSaveConsumer(value -> config.filterElytra = value)
                    .build());
            filter.addEntry(entry.startTextDescription(section("misc")).build());
            filter.addEntry(entry.startBooleanToggle(t("filter.end_crystal"), config.filterEndCrystal)
                    .setDefaultValue(true)
                    .setSaveConsumer(value -> config.filterEndCrystal = value)
                    .build());
            filter.addEntry(entry.startBooleanToggle(t("filter.anchor"), config.filterAnchor)
                    .setDefaultValue(true)
                    .setSaveConsumer(value -> config.filterAnchor = value)
                    .build());
            filter.addEntry(entry.startBooleanToggle(t("filter.glowstone"), config.filterGlowstone)
                    .setDefaultValue(true)
                    .setSaveConsumer(value -> config.filterGlowstone = value)
                    .build());
            filter.addEntry(entry.startBooleanToggle(t("filter.cobweb"), config.filterCobweb)
                    .setDefaultValue(true)
                    .setSaveConsumer(value -> config.filterCobweb = value)
                    .build());
            filter.addEntry(entry.startBooleanToggle(t("filter.fishing_rod"), config.filterFishingRod)
                    .setDefaultValue(false)
                    .setSaveConsumer(value -> config.filterFishingRod = value)
                    .build());
            filter.addEntry(entry.startBooleanToggle(t("filter.steak"), config.filterSteak)
                    .setDefaultValue(false)
                    .setSaveConsumer(value -> config.filterSteak = value)
                    .build());
            filter.addEntry(entry.startBooleanToggle(t("filter.flint_and_steel"), config.filterFlintAndSteel)
                    .setDefaultValue(false)
                    .setSaveConsumer(value -> config.filterFlintAndSteel = value)
                    .build());
            filter.addEntry(entry.startBooleanToggle(t("filter.armor_stand"), config.filterArmorStand)
                    .setDefaultValue(false)
                    .setSaveConsumer(value -> config.filterArmorStand = value)
                    .build());
            filter.addEntry(entry.startBooleanToggle(t("filter.lava_bucket"), config.filterLavaBucket)
                    .setDefaultValue(true)
                    .setSaveConsumer(value -> config.filterLavaBucket = value)
                    .build());
            filter.addEntry(entry.startBooleanToggle(t("filter.water_bucket"), config.filterWaterBucket)
                    .setDefaultValue(false)
                    .setSaveConsumer(value -> config.filterWaterBucket = value)
                    .build());
            filter.addEntry(entry.startBooleanToggle(t("filter.obsidian"), config.filterObsidian)
                    .setDefaultValue(false)
                    .setSaveConsumer(value -> config.filterObsidian = value)
                    .build());

            return builder.build();
        };
    }
}
