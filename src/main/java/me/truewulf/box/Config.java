package me.truewulf.box;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Config {
    private static final Path file = FabricLoader.getInstance().getConfigDir().resolve("combat-hitboxes.json");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static Config instance;

    public boolean enabled = true;
    public boolean hideArrow = false;
    public boolean hideFireworks = true;
    public int hitBoxColor = 0xFFFFFFFF;
    public int targetBoxColor = 0xFFFF0000;
    public int eyeColor = 0xFFFF0000;
    public int lookColor = 0xFF0000FF;
    public boolean renderEyeHeight = true;
    public boolean renderLookDir = true;
    public boolean lineLookDir = false;

    public boolean shieldColorEnabled = true;
    public int shieldColor = 0xFFFFCF40;
    public boolean shieldOnlyTarget = false;

    public boolean pearlsEnabled = true;
    public boolean pearlOwn = true;
    public boolean pearlOthers = true;
    public boolean pearlUnknown = true;
    public int pearlOwnColor = 0xFF0055FF;
    public int pearlEnemyColor = 0xFFFF3333;
    public int pearlUnknownColor = 0xFF3355CC;
    public float pearlLineWidth = 2.5f;

    public boolean weaponFilterEnabled = false;
    public boolean filterOffHand = true;
    public boolean filterSword = true;
    public boolean filterAxe = true;
    public boolean filterPickaxe = false;
    public boolean filterShovel = false;
    public boolean filterMace = true;
    public boolean filterTrident = true;
    public boolean filterSpear = true;
    public boolean filterBow = true;
    public boolean filterCrossbow = true;
    public boolean filterEnderPearl = true;
    public boolean filterWindCharge = true;
    public boolean filterGoldenApple = true;
    public boolean filterXpBottle = false;
    public boolean filterTotem = true;
    public boolean filterFirework = false;
    public boolean filterSplashPotion = false;
    public boolean filterGoldenCarrot = false;
    public boolean filterElytra = true;
    public boolean filterEndCrystal = true;
    public boolean filterAnchor = true;
    public boolean filterGlowstone = true;
    public boolean filterCobweb = true;
    public boolean filterFishingRod = false;
    public boolean filterSteak = false;
    public boolean filterFlintAndSteel = false;
    public boolean filterArmorStand = false;
    public boolean filterLavaBucket = true;
    public boolean filterWaterBucket = false;
    public boolean filterObsidian = false;
    public boolean filterOther = true;

    public float line1 = 2.5f;
    public double distFor2 = 32;
    public float line2 = 2.5f;
    public boolean outlineEnabled = false;
    public int outlineColor = 0xFF000000;
    public float outlineMultiplier = 2;

    public void save() {
        normalizeColors();
        try {
            Files.writeString(file, GSON.toJson(this));
        } catch (IOException e) {
            Main.LOGGER.error("Combat Hitboxes+ could not save the config", e);
        }
    }

    public static Config getInstance() {
        if (instance == null) {
            JsonObject json = null;
            boolean fileExists = Files.exists(file);
            if (fileExists) {
                try {
                    json = JsonParser.parseString(Files.readString(file)).getAsJsonObject();
                } catch (Exception exception) {
                    Main.LOGGER.warn("Combat Hitboxes+ couldn't parse the config, using defaults");
                }
            }
            if (json != null) {
                try {
                    instance = GSON.fromJson(json, Config.class);
                } catch (Exception exception) {
                    Main.LOGGER.warn("Combat Hitboxes+ couldn't read the config, using defaults");
                }
            }
            if (instance == null) {
                instance = new Config();
            }
            instance.migrateLegacy(json);
            instance.normalizeColors();
        }
        return instance;
    }

    private void migrateLegacy(JsonObject old) {
        if (old == null) {
            return;
        }
        if (old.has("shieldHitboxEnabled") && !old.has("shieldColorEnabled")) {
            shieldColorEnabled = old.get("shieldHitboxEnabled").getAsBoolean();
        }
        if (targetBoxColor == 0xFFFF5555 || targetBoxColor == 0xFFFF0000) {
            targetBoxColor = 0xFFFF0000;
        }
        if ((shieldColor & 0xFFFFFF) == 0xFF9B00 || (shieldColor & 0xFFFFFF) == 0x9B30FF) {
            shieldColor = 0xFFFFCF40;
        }
        lineLookDir = false;
    }

    private void normalizeColors() {
        hitBoxColor = withOpaqueAlpha(hitBoxColor);
        targetBoxColor = withOpaqueAlpha(targetBoxColor);
        shieldColor = withOpaqueAlpha(shieldColor);
        eyeColor = withOpaqueAlpha(eyeColor);
        lookColor = withOpaqueAlpha(lookColor);
        pearlOwnColor = withOpaqueAlpha(pearlOwnColor);
        pearlEnemyColor = withOpaqueAlpha(pearlEnemyColor);
        pearlUnknownColor = withOpaqueAlpha(pearlUnknownColor);
        outlineColor = withOpaqueAlpha(outlineColor);
    }

    private static int withOpaqueAlpha(int color) {
        return (color & 0xFF000000) == 0 ? color | 0xFF000000 : color;
    }
}
