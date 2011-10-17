package net.lotrcraft.strategycraft.units;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.lotrcraft.strategycraft.Config;
import net.lotrcraft.strategycraft.buildings.Building;
import net.lotrcraft.strategycraft.buildings.BuildingManager;
import net.lotrcraft.strategycraft.buildings.Castle;

import org.bukkit.Location;

public class UnitManager {
	//private static List<Castle> castles = new ArrayList<Castle>();
	private static Map<String, Class<? extends Unit>> units = new TreeMap<String, Class<? extends Unit>>();
	
	/**
	 * Adds new type of unit.
	 * @param unit Class of unit to add
	 * @return True if the unit doesn't already exist.
	 */
	public static boolean addUnitType(Class unit){
		if (unit.getName() == null || units.containsKey(unit.getName())){
			return false;
		}
		
		units.put(unit.getName(), unit);
		return true;
	}
	
	public static Unit getUnitAtLoc(Location l){
		List<Unit> tmpU = getCastles();
		
		for (int y = 0; y < tmpU.size(); y++){
			if (tmpU.get(y).getLocation().equals(l))
				return tmpU.get(y);
		}
		
		return null;
	}
	
	/**
	 * Gets the building at a specified location. 
	 * @param location The location of the core block of the alleged building.
	 * @return Null if no building is found at the location; The building if otherwise.
	 */
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
	 * If it can't find the castle, returns null.
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
	public static Class<? extends Building> getBuildingClass(String name) {
		return bldgs.get(name);
	}
	
	
	
	public static Unit getNewUnit(String name){
		try {
			return (bldgs.get(name).newInstance());
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
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
