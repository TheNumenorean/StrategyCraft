package net.lotrcraft.strategycraft;

import java.util.logging.Logger;

import net.lotrcraft.strategycraft.listeners.BlockBreakListener;
import net.lotrcraft.strategycraft.listeners.BlockPlaceListener;
import net.lotrcraft.strategycraft.listeners.SignPlaced;

import org.bukkit.Bukkit;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.Configuration;

public class Main extends JavaPlugin {
	
	public static Configuration config;
	public static PluginManager pm;
	public static Logger log = Logger.getLogger("minecraft");

	@Override
	public void onDisable() {
		log.info("StrategyCraft disabled!");

	}

	@Override
	public void onEnable() {
		
		config = this.getConfiguration();
		Config.load();
		
		pm = this.getServer().getPluginManager();
		pm.registerEvent(Type.BLOCK_BREAK, new BlockBreakListener(), Priority.Normal, this);
		//pm.registerEvent(Type.BLOCK_PLACE, new BlockPlaceListener(), Priority.Normal, this);
		pm.registerEvent(Type.SIGN_CHANGE, new SignPlaced(), Priority.Normal, this);
		
		Bukkit.getScheduler().scheduleAsyncRepeatingTask(this, new ConfSaver(this), Config.saveFrequency, Config.saveFrequency);
		

		
		log.info("StrategyCraft enabled!");
	}

}
