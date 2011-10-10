package net.lotrcraft.strategycraft.buildings;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.lotrcraft.strategycraft.Config;

import org.bukkit.Location;

public class BuildingManager {
	private static List<Castle> castles = new ArrayList<Castle>();
	private static Map<String, Class<? extends Building>> bldgs = new TreeMap<String, Class<? extends Building>>();
	
	public static boolean addCastle(String playerName, Castle castle){
		if (!castles.contains(playerName)){
			castles.add(castle);
			return true;
		}
		return false;
	}
	
	public static boolean addBuildingType(Class building){
		if (building.getName() == null || bldgs.containsKey(building.getName())){
			return false;
		}

		bldgs.put(building.getName(), building);
		return true;
	}
	
	public static List<Castle> getCastles(){
		return castles;
	}
	
	public static Castle getCastleAtLoc(Location l){
		List<Castle> tmpC = getCastles();
		
		for (int y = 0; y < tmpC.size(); y++){
			if (tmpC.get(y).getLocation().equals(l))
				return tmpC.get(y);
		}
		
		return null;
	}
	
	public static Building getBuildingAtLoc(Location location){
		Castle[] tmpC = (Castle[]) BuildingManager.getCastles().toArray();
		if (tmpC.length == 0)
			return null;
		for (int y = 0; y < tmpC.length; y++){
			if (tmpC[y].getBuildingAtLoc(location) != null){
				return tmpC[y].getBuildingAtLoc(location);
			}
		}
		return null;
	}
	
	/**
	 * This atempts to create a new castle by building a Citadel on the 
	 * supplied coords.
	 * 
	 * @param name The player's name who built the castle
	 * @param location The location to build the castle at
	 * @return Whether this action was successful
	 */
	public static void createNewCastle(String name, Location location){
		castles.add(new Castle(location, name));
	}
	

	/**
	 * Trys to find a castle owned by the named player.
	 * If it cant find the castle, returns null.
	 * 
	 * @param playerName
	 * @return The player's castle
	 */
	public static Castle getCastle(String playerName) {
		
		for (int y = 0; y < castles.size(); y++){
			if (castles.get(y).getOwner().equals(playerName))
					return castles.get(y);
		}
		return null;
	}

	/**
	 * Finds and returns the class of the type of building specified. 
	 * Returns null if it cant be found.
	 * 
	 * @param name Name of building
	 * @return The Class of the building; null if it doesnt exist.
	 */
	public static Class<? extends Building> getBuilding(String name) {
		
		return bldgs.get(name);
	}
	
	public static Object getBuildingObj(String name){
		
		
		return (bldgs.get(name));
	}
	
	/**
	 * 
	 * Completely destroys the supplied castle and its buildings.
	 * 
	 * @param castle The castle to be destroyed.
	 */
	public static void destroyCastle(Castle castle){
		castle.destroy();
		castles.remove(castle.getOwner());
		Config.removePlayerConf(castle.getOwner());
	}

}
