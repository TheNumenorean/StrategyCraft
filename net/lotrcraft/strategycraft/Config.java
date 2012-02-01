/**
 * This file will undergo massive changes once i get around to updateing it for the newer Configuration versions,
 *  and once it is updated for MySql.
 *  
 *  TODO: Update to FileConfiguration
 *  TODO: Change to MySql
 */
package net.lotrcraft.strategycraft;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import net.lotrcraft.strategycraft.buildings.Building;
import net.lotrcraft.strategycraft.buildings.BuildingManager;
import net.lotrcraft.strategycraft.buildings.Castle;
import net.lotrcraft.strategycraft.loader.BuildingLoader;
import net.lotrcraft.strategycraft.loader.InvalidBuildingConfException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.config.Configuration;

public class Config {
	
	public static int coreBlock;
	
	static Configuration playerConfig;
	static Building tmpB;
	static Castle castle;
	public static File playerDataFolder = new File("plugins" + File.separator + "StrategyCraft" + File.separator + "PlayerCastles");
	public static File buildingFolder = new File("plugins" + File.separator + "StrategyCraft" + File.separator + "Buildings");
	public static long saveFrequency;
	public static int maxBuildings;

	public static void load() {
		
		//TODO: Change to New configuration
		//TODO: Change to MySQL!
		
		StrategyCraft.log.info("[StrategyCraft] Loading Buildings...");
		
		//Make sure building folder exists
		if (!buildingFolder.exists()){
			buildingFolder.mkdirs();
			return;
		} else {
		
			File[] buildings = buildingFolder.listFiles();
			if (buildings.length != 0){
				
				for (int y = 0; y < buildings.length; y++){
					if (!buildings[y].getName().endsWith(".jar") || !buildings[y].isFile())
						continue;
					
					StrategyCraft.log.info("[StrategyCraft] Found building " + buildings[y].getName() +", attempting to load...");
					
					try {
						
						//Passes file to BuildingLoader. Will return true if successful
						if (!BuildingManager.addBuildingType(BuildingLoader.loadBuilding(buildings[y]))){
							StrategyCraft.log.warning("[StrategyCraft] Building " + buildings[y].getName() +" unable to load, check to make sure you dont have duplicates in your buildings folder.");
							continue;
						}
					} catch (IOException e) {
						e.printStackTrace();
						continue;
					} catch (InvalidBuildingConfException e) {
						e.printStackTrace();
						continue;
					}
					
					StrategyCraft.log.info("[StrategyCraft] Building " + buildings[y].getName() +" successfully loaded!");
				}
				
			} else {
				
				//Since there arent any buildings, return, and let main file realize lack of Citadel
				StrategyCraft.log.severe("[StrategyCraft] Can't find any buildings!");
				return;
			}
		}
		
		//Check to make sure it loaded a Citadel building
		if (BuildingManager.getBuilding("Citadel") == null) {
			return;
		}
		
		StrategyCraft.log.info("[StrategyCraft] Finished loading buildings.");
	
		//Gets values from main config file
		//TODO: Update
		Configuration config = StrategyCraft.config;
		config.load();
		coreBlock =  getInt("coreBlock", 49, config);
		saveFrequency = getInt("saveFrequency", 3600, config );
		maxBuildings = getInt("maxBuildings", 10, config );
		
		StrategyCraft.log.info("[StrategyCraft] Loading Castles...");
		
		//Load Player files
		//TODO: replace with MySql! This section is very ineffective, and will not load all buildings.
		
		//Check to make sure playerDataFolder exists
		if (!playerDataFolder.exists()){
			playerDataFolder.mkdirs();
		} else {
			File[] playerFiles = playerDataFolder.listFiles();
			if (playerFiles == null) return;
			
			for (int counter = 0; counter < playerFiles.length; counter++){
				
				if (!playerFiles[counter].getName().endsWith(".yml"))
					continue;
				
				String playerName = playerFiles[counter].getName().substring(0, playerFiles[counter].getName().indexOf('.'));
				
				if (!playerFiles[counter].canRead()){
					StrategyCraft.log.warning("Can't read file!");
				}
				
				playerConfig = new Configuration(playerFiles[counter]);
				playerConfig.load();
				
				//Get Citadel x coordinate
				if (isNull("Castle.Citadel.X", playerConfig)){
					StrategyCraft.log.info("[StrategyCraft] Config for " + playerName + " missing X for citadel. Rejecting...");
					continue;
				}
				int x = playerConfig.getInt("Castle.Citadel.X", -1);
				
				//Get Citadel y coordinate
				if (isNull("Castle.Citadel.Y", playerConfig)){
					StrategyCraft.log.info("[StrategyCraft] Config for " + playerName + " missing Y for citadel. Rejecting...");
					continue;
				}
				int y = playerConfig.getInt("Castle.Citadel.Y", -1);
				
				//Get Citadel z coordinate
				if (isNull("Castle.Citadel.Z", playerConfig)){
					StrategyCraft.log.info("[StrategyCraft] Config for " + playerName + " missing Z for citadel. Rejecting...");
					continue;
				}
				int z = playerConfig.getInt("Castle.Citadel.Z", -1);
				
				//Get citadel World
				if (isNull("Castle.Citadel.World", playerConfig)){
					StrategyCraft.log.info("[StrategyCraft] Config for " + playerName + " missing World for citadel. Rejecting...");
					continue;
				}
				World world = Bukkit.getWorld(playerConfig.getString("Castle.Citadel.World", "world"));
				
				if (world == null) {
					StrategyCraft.log.info("[StrategyCraft] Server is missing world defined for " + playerName + "'s citadel. Rejecting...");
					continue;
				}
				
				//Get new location from acquired data 
				Location location = new Location(world, x, y, z);
				
				//Add new castle into list
				castle = new Castle(location, playerName);
				BuildingManager.addCastle(playerName, castle);
				
				playerConfig.save();
				
				StrategyCraft.log.info("[StrategyCraft] Castle for " + playerName + " loaded!");
				
			}
		}
		
		//This definitely doesnt send a string to the logger
		StrategyCraft.log.info("[StrategyCraft] Finished loading castles.");
		
		StrategyCraft.config.save();
	}
	
	/**
	 * Get a property from a given location. If the location is null, it will be set to the value of def.
	 * @param path Path to node
	 * @param def Default value
	 * @param config Config to read
	 * @return The recieved object, def if none is found.
	 */

	public Object getProperty(String path, Object def, Configuration config) {
		if(isNull(path, config))
			return setProperty(path, def, config);
		return config.getProperty(path);
	}

	/**
	 * Get an int at a given location. If the location is null, it will be set to the value of def.
	 * @param path Path to node
	 * @param def Default value
	 * @param config Config to read
	 * @return Recieved value, or def if none is found
	 */
	public static int getInt(String path, Integer def, Configuration config) {
		if(isNull(path, config))
			return (Integer) setProperty(path, def, config);
		return config.getInt(path, def);
	}

	/**
	 * Gets a boolean at a given location. If the location points to a null object as defined by isNull , will set the node to the given value.
	 * @param path Path to node
	 * @param def Default value
	 * @param config Config to use
	 * @return The value, or def if the link is null.
	 */
	public static boolean getBoolean(String path, Boolean def, Configuration config) {
		if(isNull(path, config))
			return (Boolean) setProperty(path, def, config);
		return config.getBoolean(path, def);
	}

	/**
	 * Sets the defined path to the object's value
	 * @param path Path to node
	 * @param val Value to use
	 * @param config Config to use
	 * @return The given object
	 */
	private static Object setProperty(String path, Object val, Configuration config) {
		config.setProperty(path, val);
		return val;
	}

	/**
	 * Checks to see whether a specified path points to a null object
	 * @param path Path to configuration node
	 * @param config The configuration to check
	 * @return Whether the specified path points to an existant node 
	 */
	private static boolean isNull(String path, Configuration config) {
		return config.getProperty(path) == null;
	}

	/**
	 * Deletes a configuration file for a certain Player
	 * @param owner The name of the player whose conf is to be deleted.
	 */
	public static void removePlayerConf(String owner) {
		File file = new File(File.pathSeparator + "StrategyCraft" + File.pathSeparator + "PlayerCastles" + File.pathSeparator + owner + ".yml");
		if (file.exists()){
			file.delete();
		}
	}
}
