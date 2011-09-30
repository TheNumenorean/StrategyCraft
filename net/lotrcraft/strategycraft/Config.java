package net.lotrcraft.strategycraft;

import java.io.File;

import net.lotrcraft.strategycraft.buildings.Building;
import net.lotrcraft.strategycraft.buildings.BuildingManager;
import net.lotrcraft.strategycraft.buildings.Castle;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Creeper;
import org.bukkit.util.config.Configuration;

public class Config {
	
	public static int coreBlock;
	static //public static boolean 

	
	Configuration playerConfig;
	static Building tmpB;
	static Castle castle;
	public static File playerDataFolder = new File(File.separator + "StrategyCraft" + File.separator + "PlayerCastles");
	public static File playerDataFile;
	public static long saveFrequency;

	public static void load() {
	
		Configuration config = Main.config;
		config.load();
		coreBlock =  getInt("coreBlock", 49, config);
		saveFrequency = getInt("saveFrequency", 3600, Main.config );
		
		Main.log.info("[StrategyCraft] Loading Castles...");
		
		if (!playerDataFolder.exists()){
			playerDataFolder.mkdirs();
			playerDataFolder.mkdir();
		} else {
			File[] playerFiles = playerDataFolder.listFiles();
			if (playerFiles == null) return;
			
			for (int counter = 0; counter < playerFiles.length; counter++){
				
				if (!playerFiles[counter].getName().endsWith(".yml"))
					continue;
				
				String playerName = playerFiles[counter].getName().substring(0, playerFiles[counter].getName().indexOf('.'));
				
				playerDataFile = new File(playerDataFolder.getPath() + File.pathSeparator + playerFiles[counter]);
				playerConfig = new Configuration(playerDataFile);
				
				if (isNull("Castle.Citadel.X", playerConfig)){
					Main.log.info("[StrategyCraft] Config for " + playerName + " missing X for citadel. Rejecting...");
					continue;
				}
				int x = playerConfig.getInt("Castle.Citadel.X", -1);
				if (isNull("Castle.Citadel.Y", playerConfig)){
					Main.log.info("[StrategyCraft] Config for " + playerName + " missing Y for citadel. Rejecting...");
					continue;
				}
				int y = playerConfig.getInt("Castle.Citadel.Y", -1);
				if (isNull("Castle.Citadel.Z", playerConfig)){
					Main.log.info("[StrategyCraft] Config for " + playerName + " missing Z for citadel. Rejecting...");
					continue;
				}
				int z = playerConfig.getInt("Castle.Citadel.Z", -1);
				World world = Bukkit.getWorld(playerConfig.getString("Castle.Citadel.world", null));
				
				if (world == null) {
					Main.log.info("[StrategyCraft] Config for " + playerName + " missing world for citadel. Rejecting...");
					continue;
				}
				
				Location location = new Location(world, x, y, z);
				
				castle = new Castle(location, playerName);
				BuildingManager.addCastle(playerName, castle);
				
				Main.log.info("[StrategyCraft] Castle for " + playerName + " loaded!");
				
			}
		}
		
		Main.log.info("[StrategyCraft] Finished loading castles.");
		
		Main.config.save();
	}

	public Object getProperty(String path, Object def, Configuration config) {
		if(isNull(path, config))
			return setProperty(path, def, config);
		return config.getProperty(path);
	}

	public static int getInt(String path, Integer def, Configuration config) {
		if(isNull(path, config))
			return (Integer) setProperty(path, def, config);
		return config.getInt(path, def);
	}

	public static Boolean getBoolean(String path, Boolean def, Configuration config) {
		if(isNull(path, config))
			return (Boolean) setProperty(path, def, config);
		return config.getBoolean(path, def);
	}

	private static Object setProperty(String path, Object val, Configuration config) {
		config.setProperty(path, val);
		return val;
	}

	private static boolean isNull(String path, Configuration config) {
		return Main.config.getProperty(path) == null;
	}

	public static void removePlayerConf(String owner) {
		File file = new File(File.pathSeparator + "StrategyCraft" + File.pathSeparator + "PlayerCastles" + File.pathSeparator + owner + ".yml");
		if (file.exists()){
			file.delete();
		}
	}
}
