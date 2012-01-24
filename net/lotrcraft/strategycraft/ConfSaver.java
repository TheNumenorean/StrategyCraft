package net.lotrcraft.strategycraft;

import java.io.File;
import java.io.IOException;
import java.util.List;

import net.lotrcraft.strategycraft.buildings.BuildingManager;
import net.lotrcraft.strategycraft.buildings.Castle;

import org.bukkit.plugin.Plugin;
import org.bukkit.util.config.Configuration;

public class ConfSaver implements Runnable {
	private Plugin p;

	public ConfSaver(Plugin p) {
		this.p = p;
	}

	@Override
	public void run() {
		
		//TODO: Change to MySQL!
		
		//Just in case
		Config.playerDataFolder.mkdirs();
		
		//If there are no castle to save
		if (BuildingManager.getCastles().isEmpty())
			return;
		
		StrategyCraft.log.info("[StrategyCraft] Saving castles...");
		
		List<Castle> c = BuildingManager.getCastles();
		
		for (Castle castle : c){
			//Gets next acceptable player data file 
			File file = new File(Config.playerDataFolder.getPath() + File.separator + castle.getOwner() + ".yml");
			try {
				file.createNewFile();
			} catch (IOException e) {
				StrategyCraft.log.severe("Can't save Player files!");
			}
			
			//TODO: Update to new Config
			//Gets config for player and saves Citadel Data
			Configuration config = new Configuration(file);
			
			config.setProperty("Castle.Citadel.X", castle.getLocation().getBlockX());
			config.setProperty("Castle.Citadel.Y", castle.getLocation().getBlockY());
			config.setProperty("Castle.Citadel.Z", castle.getLocation().getBlockZ());
			config.setProperty("Castle.Citadel.World", castle.getLocation().getWorld().getName());
			
			//TODO: Add building save data (not until MySQL added)
			
			config.save();
			
			StrategyCraft.log.info("[StrategyCraft] Castle for " + castle.getOwner() + " saved!");
		}
		
		StrategyCraft.log.info("[StrategyCraft] Finished saving!");
		
		return;
		
	}

}
