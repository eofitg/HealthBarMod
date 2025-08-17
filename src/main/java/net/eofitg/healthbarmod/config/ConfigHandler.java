package net.eofitg.healthbarmod.config;

import net.eofitg.healthbarmod.hud.PlayerHealthBarRenderer;
import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class ConfigHandler {
    private final Configuration config;

    public ConfigHandler(File file) {
        this.config = new Configuration(file);
    }

    public void load() {
        config.load();

        PlayerHealthBarRenderer.ENABLED = config.getBoolean("enabled", "general", true, "Whether health bar rendering is enabled");
        PlayerHealthBarRenderer.SHOW_SELF = config.getBoolean("showSelf", "general", false, "Whether to show own health bar");
        PlayerHealthBarRenderer.HIDE_WHEN_SNEAKING = config.getBoolean("hideWhenSneaking", "general", false, "Hide health bar when sneaking");
        PlayerHealthBarRenderer.MAX_DISTANCE = config.getFloat("maxDistance", "general", 24.0f, 1.0f, 128.0f, "Maximum render distance");
        PlayerHealthBarRenderer.SCALE = config.getFloat("scale", "general", 0.0165f, 0.005f, 0.05f, "Overall scale size");
        PlayerHealthBarRenderer.X_OFFSET = config.getFloat("xOffset", "general", 0f, -5f, 5f, "Horizontal offset of overall");
        PlayerHealthBarRenderer.Y_OFFSET = config.getFloat("yOffset", "general", 0f, -2.5f, 2.5f, "Vertical offset of overall");
        PlayerHealthBarRenderer.Z_OFFSET = config.getFloat("zOffset", "general", 0f, -5f, 5f, "Depth offset of overall");
        PlayerHealthBarRenderer.BAR_WIDTH = config.getFloat("barWidth", "general", 70f, 10f, 200f, "Health bar width");
        PlayerHealthBarRenderer.BAR_HEIGHT = config.getFloat("barHeight", "general", 7f, 2f, 20f, "Health bar height");
        PlayerHealthBarRenderer.BAR_MARGIN = config.getFloat("barMargin", "general", 10f, 0f, 40f, "Margin between health bar and name");
        PlayerHealthBarRenderer.BAR_ROTATION = config.getFloat("barRotation", "general", 0f, -180f, 180f, "Health bar rotation");
        PlayerHealthBarRenderer.BAR_X_OFFSET = config.getFloat("barXOffset", "general", 0f, -5f, 5f, "Horizontal offset of health bar");
        PlayerHealthBarRenderer.BAR_Y_OFFSET = config.getFloat("barYOffset", "general", 0f, -2.5f, 2.5f, "Vertical offset of health bar");
        PlayerHealthBarRenderer.BAR_Z_OFFSET = config.getFloat("barZOffset", "general", 0f, -5f, 5f, "Depth offset of health bar");

        if (config.hasChanged()) {
            config.save();
        }
    }

    public void save() {
        config.get("general", "enabled", true).set(PlayerHealthBarRenderer.ENABLED);
        config.get("general", "showSelf", false).set(PlayerHealthBarRenderer.SHOW_SELF);
        config.get("general", "hideWhenSneaking", false).set(PlayerHealthBarRenderer.HIDE_WHEN_SNEAKING);
        config.get("general", "maxDistance", 24.0).set(PlayerHealthBarRenderer.MAX_DISTANCE);
        config.get("general", "scale", 0.0165).set(PlayerHealthBarRenderer.SCALE);
        config.get("general", "xOffset", 0f).set(PlayerHealthBarRenderer.X_OFFSET);
        config.get("general", "yOffset", 0f).set(PlayerHealthBarRenderer.Y_OFFSET);
        config.get("general", "zOffset", 0f).set(PlayerHealthBarRenderer.Z_OFFSET);
        config.get("general", "barWidth", 70.0).set(PlayerHealthBarRenderer.BAR_WIDTH);
        config.get("general", "barHeight", 7.0).set(PlayerHealthBarRenderer.BAR_HEIGHT);
        config.get("general", "barMargin", 10.0).set(PlayerHealthBarRenderer.BAR_MARGIN);
        config.get("general", "barRotation", 0.0).set(PlayerHealthBarRenderer.BAR_ROTATION);
        config.get("general", "barXOffset", 0.0).set(PlayerHealthBarRenderer.BAR_X_OFFSET);
        config.get("general", "barYOffset", 0.0).set(PlayerHealthBarRenderer.BAR_Y_OFFSET);
        config.get("general", "barZOffset", 0.0).set(PlayerHealthBarRenderer.BAR_Z_OFFSET);

        config.save();
    }
}
