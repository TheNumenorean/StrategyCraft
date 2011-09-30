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
	private static Map<String, Building> bldgs = new TreeMap<String, Building>();
	
	public static boolean addCastle(String playerName, Castle castle){
		if (!castles.contains(playerName)){
			castles.add(castle);
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
		return null;
	}
	
	/*
	public static boolean addBuildingToCastle(Castle castle, Building building){
		castle.addBuilding(building)
	}
	*/
	
	public static void createNewCastle(String name, Location l){
		castles.add(new Castle(l, name));
	}

	public static Castle getCastle(String playerName) {
		
		for (int y = 0; y < castles.size(); y++){
			if (castles.get(y).getOwner().equals(playerName))
					return castles.get(y);
		}
		return null;
	}

	public static Building getBuilding(String string) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static void destroyCastle(Castle castle){
		castle.destroy();
		castles.remove(castle.getOwner());
		Config.removePlayerConf(castle.getOwner());
	}

}
