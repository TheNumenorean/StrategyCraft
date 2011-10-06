package net.lotrcraft.strategycraft.buildings;

import java.util.ArrayList;
import java.util.List;

import net.lotrcraft.strategycraft.Config;

import org.bukkit.Bukkit;
import org.bukkit.Location;
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
		citadel = (Building) (BuildingManager.getBuilding("Citadel").cast(Building.class));
		citadel.build(l);
		createNewCastle();
	}
	
	public Building getBuildingAtLoc(Location l){
		return null;
	}
	
	public boolean isBuildingLeft(){
		return !buildings.isEmpty();
	}

	private void createNewCastle() {
		buildings.add(citadel);
		
		
	}
	
	public boolean addBuilding(Building building){
		if (buildings.size() < Config.maxBuildings)buildings.add(building);
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
