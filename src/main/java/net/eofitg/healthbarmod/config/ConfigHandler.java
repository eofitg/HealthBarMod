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

        PlayerHealthBarRenderer.ENABLED = config.getBoolean("enabled", "general", DefaultConfig.enabled, "Whether health bar rendering is enabled");
        PlayerHealthBarRenderer.SHOW_SELF = config.getBoolean("showSelf", "general", DefaultConfig.showSelf, "Whether to show own health bar");
        PlayerHealthBarRenderer.HIDE_WHEN_SNEAKING = config.getBoolean("hideWhenSneaking", "general", DefaultConfig.hideWhenSneaking, "Whether to hide health bar when sneaking");
        PlayerHealthBarRenderer.FACE_PLAYER = config.getBoolean("facePlayer", "general", DefaultConfig.facePlayer, "Whether to keep health bar always facing player");
        PlayerHealthBarRenderer.TEAM_COLOR = config.getBoolean("teamColor", "general", DefaultConfig.teamColor, "Whether to render text with specific team color");
        PlayerHealthBarRenderer.MAX_DISTANCE = config.getFloat("maxDistance", "general", DefaultConfig.maxDistance, 1.0f, 128.0f, "Maximum render distance");
        PlayerHealthBarRenderer.SCALE = config.getFloat("scale", "general", DefaultConfig.scale, 0.005f, 0.05f, "Overall scale size");
        PlayerHealthBarRenderer.X_OFFSET = config.getFloat("xOffset", "general", DefaultConfig.xOffset, -5f, 5f, "Horizontal offset of overall");
        PlayerHealthBarRenderer.Y_OFFSET = config.getFloat("yOffset", "general", DefaultConfig.yOffset, -2.5f, 2.5f, "Vertical offset of overall");
        PlayerHealthBarRenderer.Z_OFFSET = config.getFloat("zOffset", "general", DefaultConfig.zOffset, -5f, 5f, "Depth offset of overall");
        PlayerHealthBarRenderer.BAR_WIDTH = config.getFloat("barWidth", "general", DefaultConfig.barWidth, 1f, 100f, "Health bar width");
        PlayerHealthBarRenderer.BAR_HEIGHT = config.getFloat("barHeight", "general", DefaultConfig.barHeight, 1f, 100f, "Health bar height");
        PlayerHealthBarRenderer.BAR_MARGIN = config.getFloat("barMargin", "general", DefaultConfig.barMargin, 0f, 40f, "Margin between health bar and name");
        PlayerHealthBarRenderer.BAR_ROTATION = config.getFloat("barRotation", "general", DefaultConfig.barRotation, -180f, 180f, "Health bar rotation");
        PlayerHealthBarRenderer.BAR_X_OFFSET = config.getFloat("barXOffset", "general", DefaultConfig.barXOffset, -200f, 200f, "Horizontal offset of health bar");
        PlayerHealthBarRenderer.BAR_Y_OFFSET = config.getFloat("barYOffset", "general", DefaultConfig.barYOffset, -100f, 100f, "Vertical offset of health bar");
        PlayerHealthBarRenderer.BAR_Z_OFFSET = config.getFloat("barZOffset", "general", DefaultConfig.barZOffset, -200f, 200f, "Depth offset of health bar");

        if (config.hasChanged()) {
            config.save();
        }
    }

    public void save() {
        config.get("general", "enabled", DefaultConfig.enabled).set(PlayerHealthBarRenderer.ENABLED);
        config.get("general", "showSelf", DefaultConfig.showSelf).set(PlayerHealthBarRenderer.SHOW_SELF);
        config.get("general", "hideWhenSneaking", DefaultConfig.hideWhenSneaking).set(PlayerHealthBarRenderer.HIDE_WHEN_SNEAKING);
        config.get("general", "facePlayer", DefaultConfig.facePlayer).set(PlayerHealthBarRenderer.FACE_PLAYER);
        config.get("general", "teamColor", DefaultConfig.teamColor).set(PlayerHealthBarRenderer.TEAM_COLOR);
        config.get("general", "maxDistance", DefaultConfig.maxDistance).set(PlayerHealthBarRenderer.MAX_DISTANCE);
        config.get("general", "scale", DefaultConfig.scale).set(PlayerHealthBarRenderer.SCALE);
        config.get("general", "xOffset", DefaultConfig.xOffset).set(PlayerHealthBarRenderer.X_OFFSET);
        config.get("general", "yOffset", DefaultConfig.yOffset).set(PlayerHealthBarRenderer.Y_OFFSET);
        config.get("general", "zOffset", DefaultConfig.zOffset).set(PlayerHealthBarRenderer.Z_OFFSET);
        config.get("general", "barWidth", DefaultConfig.barWidth).set(PlayerHealthBarRenderer.BAR_WIDTH);
        config.get("general", "barHeight", DefaultConfig.barHeight).set(PlayerHealthBarRenderer.BAR_HEIGHT);
        config.get("general", "barMargin", DefaultConfig.barMargin).set(PlayerHealthBarRenderer.BAR_MARGIN);
        config.get("general", "barRotation", DefaultConfig.barRotation).set(PlayerHealthBarRenderer.BAR_ROTATION);
        config.get("general", "barXOffset", DefaultConfig.barXOffset).set(PlayerHealthBarRenderer.BAR_X_OFFSET);
        config.get("general", "barYOffset", DefaultConfig.barYOffset).set(PlayerHealthBarRenderer.BAR_Y_OFFSET);
        config.get("general", "barZOffset", DefaultConfig.barZOffset).set(PlayerHealthBarRenderer.BAR_Z_OFFSET);

        config.save();
    }
}
