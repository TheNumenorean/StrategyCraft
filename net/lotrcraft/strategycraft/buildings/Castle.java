package net.lotrcraft.strategycraft.buildings;

import java.util.ArrayList;
import java.util.List;

import net.lotrcraft.strategycraft.Config;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

public class Castle {
	private List<Building> buildings = new ArrayList<Building>();
	private Location l;
	private Building citadel;
	private Player player;
	private String playerName;
	
	
	public Castle (Location l, String playerName){
		this.l = l;
		this.player = Bukkit.getPlayerExact(playerName);
		this.playerName = playerName;
		//TODO: Does this work?
		citadel = (BuildingManager.getBuilding("Citadel").cast(Building.class));
		citadel.build(l);
		createNewCastle();
	}
	
	public Building getBuildingAtLoc(Location l){
		for (int y = 0; y < buildings.size(); y++){
			if (buildings.get(y).location == l)
				return buildings.get(y);
		}
		return null;
	}
	
	public boolean isBuildingLeft(){
		return !buildings.isEmpty();
	}

	private void createNewCastle() {
		buildings.add(citadel);
		
		
	}
	
	/**
	 * Adds a building to this castle and builds it, but onl if the castle
	 * has enough room left. Returns true if successful.
	 * 
	 * @param building Building to add.
	 * @param blockFace 
	 * @param blockLoc 
	 * @return True if succesful
	 */
	public boolean addBuilding(Building building, Location blockLoc, BlockFace blockFace){
		if (buildings.size() < Config.maxBuildings){
			buildings.add(building);
			building.build(blockLoc);
			return true;
		}
		return false;
	}
	
	public Location getLocation(){
		return l;
	}

	public void destroy() {
		citadel.destroy();
	}
	
	public String getOwner(){
		return playerName;
	}
	

}
