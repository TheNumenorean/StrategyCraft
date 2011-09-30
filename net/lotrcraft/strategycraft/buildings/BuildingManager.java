package net.lotrcraft.strategycraft.buildings;

import java.util.Collection;
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
	
	public static Collection<Castle> getCastles(){
		return castles.values();
	}
	
	public static Castle getCastleAtLoc(Location l){
		Castle[] tmpC = (Castle[]) BuildingManager.getCastles().toArray();
		
		for (int y = 0; y < tmpC.length; y++){
			if (tmpC[y].getLocation().equals(l))
				return tmpC[y];
		}
		
		return null;
	}
	
	public static Building getBuildingAtLoc(Location location){
		Castle[] tmpC = (Castle[]) BuildingManager.getCastles().toArray();
		return null;
	}
	
	/*
	public static boolean addBuildingToCastle(Castle castle, Building building){
		castle.addBuilding(building)
	}
	*/
	
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
