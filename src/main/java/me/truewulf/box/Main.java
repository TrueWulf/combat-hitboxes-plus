package me.truewulf.box;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.Minecraft;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main implements ClientModInitializer {
    public static Minecraft mc = Minecraft.getInstance();
    public static final Logger LOGGER = LoggerFactory.getLogger("CombatHitboxes");

    //? if <1.21.11 {
    /*public static float lineWidth = 2.5F;
     *///?}

    @Override
    public void onInitializeClient() {
        LOGGER.info("Combat Hitboxes+ loaded");
    }

    //? if <1.21.11 {
    /*public static float getVanillaWidth() {
        return Math.max(2.5F, (float) mc.getWindow().getWidth() / 1920.0F * 2.5F);
    }
     *///?}
}
