package net.lotrcraft.strategycraft.buildings;

import java.util.Map;
import java.util.TreeMap;

import org.bukkit.Location;

public class BuildingManager {
	private static Map<String, Castle> castles = new TreeMap<String, Castle>();
	private static Map<String, Building> bldgs = new TreeMap<String, Building>();
	
	public static boolean addCastle(String playerName, Castle castle){
		if (!castles.containsKey(playerName)){
			castles.put(playerName, castle);
			return true;
		}
		return false;
	}
	
	public static boolean addBuildingType(Building building){
		if (building.getName() == null || bldgs.containsKey(building.getName())){
			return false;
		}
		
		bldgs.put(building.getName(), building);
		return true;
	}
	
	public static boolean addBuildingToCastle(Castle castle, Building building){
		
	}
	
	public static void createNewCastle(String name, Location l){
		castles.put(name, new Castle(l));
	}

	public static Castle getCastle(String playerName) {
		return castles.get(playerName);
	}
	
	public static boolean castleExists(String playerName){
		return castles.containsKey(playerName);
	}

	public static Building getBuilding(String string) {
		// TODO Auto-generated method stub
		return null;
	}

}
