package net.lotrcraft.strategycraft;

import java.io.File;

import net.lotrcraft.strategycraft.buildings.Building;
import net.lotrcraft.strategycraft.buildings.BuildingManager;
import net.lotrcraft.strategycraft.buildings.Castle;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.config.Configuration;

public class Config {
	
	public static int coreBlock;
	static //public static boolean 

	
	Configuration playerConfig;
	static Building tmpB;
	static Castle castle;
	public static File playerDataFolder = new File(File.separator + "StrategyCraft" + File.separator + "PlayerCastles");
	public static File playerDataFile;
	public static long saveFrequency = 600;

	public static void loadConf() {
	
		Configuration config = Main.config;
		config.load();
		coreBlock =  getInt("coreBlock", 49, config);
		saveFrequency = getDouble("saveFrequency", 600, Main.config );
		
		if (!playerDataFolder.exists()){
			playerDataFolder.mkdirs();
			playerDataFolder.mkdir();
		} else {
			File[] playerFiles = playerDataFolder.listFiles();
			if (playerFiles == null) return;
			
			for (int counter = 0; counter < playerFiles.length; counter++){
				
				if (!playerFiles[counter].getName().endsWith(".yml"))
					continue;
				
				playerDataFile = new File(playerDataFolder.getPath() + File.pathSeparator + playerFiles[counter]);
				playerConfig = new Configuration(playerDataFile);
				
				if (isNull("Castle.Citadel.X", playerConfig))
					continue;
				int x = playerConfig.getInt("Castle.Citadel.X", -1);
				if (isNull("Castle.Citadel.Y", playerConfig))
					continue;
				int y = playerConfig.getInt("Castle.Citadel.Y", -1);
				if (isNull("Castle.Citadel.Z", playerConfig))
					continue;
				int z = playerConfig.getInt("Castle.Citadel.Z", -1);
				World world = Bukkit.getWorld(playerConfig.getString("Castle.Citadel.world", null));
				
				if (world == null) continue;
				
				Location location = new Location(world, x, y, z);
				
				castle = new Castle(location);
				BuildingManager.addCastle(playerFiles[counter].getName().substring(0, playerFiles[counter].getName().indexOf('.')), castle);
				
			}
		}
		
		Main.config.save();
	}
	
	private static long getDouble(String string, int i, Configuration config) {
		// TODO Auto-generated method stub
		return 0;
	}

	public Object getProperty(String path, Object def, Configuration config) {
		if(isNull(path, config))
			return setProperty(path, def, config);
		return Main.config.getProperty(path);
	}

	public static int getInt(String path, Integer def, Configuration config) {
		if(isNull(path, config))
			return (Integer) setProperty(path, def, config);
		return Main.config.getInt(path, def);
	}

	public static Boolean getBoolean(String path, Boolean def, Configuration config) {
		if(isNull(path, config))
			return (Boolean) setProperty(path, def, config);
		return config.getBoolean(path, def);
	}

	private static Object setProperty(String path, Object val, Configuration config) {
		Main.config.setProperty(path, val);
		return val;
	}

	private static boolean isNull(String path, Configuration config) {
		return Main.config.getProperty(path) == null;
	}

	public static Runnable saveConfs() {
		// TODO Auto-generated method stub
		return null;
	}
}
