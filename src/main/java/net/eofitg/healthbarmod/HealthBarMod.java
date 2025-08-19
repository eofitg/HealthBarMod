package net.eofitg.healthbarmod;

import net.eofitg.healthbarmod.commands.CommandHealthBar;
import net.eofitg.healthbarmod.config.ConfigHandler;
import net.eofitg.healthbarmod.hud.PlayerHealthBarRenderer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;

@Mod(
        modid = HealthBarMod.MODID,
        name = "Health Bar Mod",
        version = "1.3.1",
        acceptedMinecraftVersions = "[1.8.9]"
)
public class HealthBarMod {
    public static final String MODID = "healthbarmod";
    public static ConfigHandler configHandler;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        File configFile = new File(e.getModConfigurationDirectory(), MODID + ".cfg");
        configHandler = new ConfigHandler(configFile);
        configHandler.load();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        MinecraftForge.EVENT_BUS.register(new PlayerHealthBarRenderer());
        net.minecraftforge.client.ClientCommandHandler.instance.registerCommand(new CommandHealthBar());
    }
}
