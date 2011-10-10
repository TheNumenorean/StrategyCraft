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
		
		Config.playerDataFolder.mkdirs();
		
		if (BuildingManager.getCastles().isEmpty())
			return;
		
		Main.log.info("[StrategyCraft] Saving castles...");
		
		File file;
		List<Castle> c = BuildingManager.getCastles();
		
		for (int y = 0; y < c.size(); y++){
			file = new File(Config.playerDataFolder.getPath() + File.separator + c.get(y).getOwner() + ".yml");
			try {
				file.createNewFile();
			} catch (IOException e) {
				Main.log.severe("Can't save Player files!");
			}
			
			Configuration config = new Configuration(file);
			
			config.setProperty("Castle.Citadel.X", c.get(y).getLocation().getBlockX());
			config.setProperty("Castle.Citadel.Y", c.get(y).getLocation().getBlockY());
			config.setProperty("Castle.Citadel.Z", c.get(y).getLocation().getBlockZ());
			config.setProperty("Castle.Citadel.World", c.get(y).getLocation().getWorld().getName());
			
			config.save();
			
			Main.log.info("[StrategyCraft] Castle for " + c.get(y).getOwner() + " saved!");
		}
		
		Main.log.info("[StrategyCraft] Finished saving!");
		
		return;
		
	}

}
