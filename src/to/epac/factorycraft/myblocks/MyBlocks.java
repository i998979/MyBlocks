package to.epac.factorycraft.myblocks;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;

import to.epac.factorycraft.myblocks.commands.Commands;
import to.epac.factorycraft.myblocks.events.BreakHandler;
import to.epac.factorycraft.myblocks.events.ClickHandler;
import to.epac.factorycraft.myblocks.events.PhysicsHandler;
import to.epac.factorycraft.myblocks.events.PlaceHandler;
import to.epac.factorycraft.myblocks.events.StepHandler;
import to.epac.factorycraft.myblocks.gui.GuiHandler;
import to.epac.factorycraft.myblocks.metrics.Metrics;

public class MyBlocks extends JavaPlugin {
	
	private static MyBlocks plugin;
	public static ProtocolManager protocolManager;
	
	public static File configFile;

	public void onEnable() {
		plugin = this;
		protocolManager = ProtocolLibrary.getProtocolManager();
		
		PluginManager pm = getServer().getPluginManager();
		
		pm.registerEvents(new BreakHandler(), this);
		pm.registerEvents(new ClickHandler(), this);
		pm.registerEvents(new PhysicsHandler(), this);
		pm.registerEvents(new PlaceHandler(), this);
		pm.registerEvents(new StepHandler(), this);
		pm.registerEvents(new GuiHandler(), this);
		
		getCommand("MyBlocks").setExecutor(new Commands());
		
        configFile = new File(getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            getServer().getConsoleSender().sendMessage(ChatColor.RED + "Configuration not found. Generating the default one.");

            getConfig().options().copyDefaults(true);
            saveConfig();
        }
        
        Metrics metrics = new Metrics(this, 6642);
	}
	
	public void onDisable() {
		plugin = null;
	}
	
	public static MyBlocks inst() {
		return plugin;
	}
}
