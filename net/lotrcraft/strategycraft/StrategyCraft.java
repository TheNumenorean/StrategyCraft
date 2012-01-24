package net.lotrcraft.strategycraft;

import java.util.logging.Logger;

import net.lotrcraft.strategycraft.buildings.BuildingManager;
import net.lotrcraft.strategycraft.listeners.BlockBreakListener;
import net.lotrcraft.strategycraft.listeners.BlockPlaceListener;
import net.lotrcraft.strategycraft.listeners.SignPlaced;

import org.bukkit.Bukkit;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.Configuration;

public class StrategyCraft extends JavaPlugin {
	
	public static Configuration config;
	public static PluginManager pm;
	public static Logger log = Logger.getLogger("minecraft");

	@Override
	public void onDisable() {
		//new ConfSaver(this);
		log.info("StrategyCraft disabled!");

	}

	@Override
	public void onEnable() {
		//TODO: This was written before configuration update, needs to be re-done.
		//Loads settings from config file.
		config = this.getConfiguration();
		Config.load();
		
		pm = this.getServer().getPluginManager();
		
		//Listener for listening for whe a castle is  destroyed
		pm.registerEvent(Type.BLOCK_BREAK, new BlockBreakListener(), Priority.Normal, this);
		
		//TODO: Later to protect from meerly blowing up buildings
		//pm.registerEvent(Type.BLOCK_PLACE, new BlockPlaceListener(), Priority.Normal, this);
		
		//For registering when a new building is created
		pm.registerEvent(Type.SIGN_CHANGE, new SignPlaced(), Priority.Normal, this);
		
		//Listen for right click on sign. For unit creation
		//pm.registerEvent(Type.PLAYER_INTERACT, new PlayerInteractListener(), Priority.Normal, this);
		
		//Schedules periodic saves so as not to overload server when saving by saving too often. Done in new thread due to risk of 
		Bukkit.getScheduler().scheduleAsyncRepeatingTask(this, new ConfSaver(this), Config.saveFrequency, Config.saveFrequency);
		
		
		//Checks to make sure there is a 'citadel' building, as SC cant run without it.
		if (BuildingManager.getBuilding("Citadel") == null) {
			log.severe("[StrategyCraft] Can't find Citadel.jar! Disabling...");
			pm.disablePlugin(this);
			return;
		}
		
		log.info("StrategyCraft enabled!");
	}
	
	

}
