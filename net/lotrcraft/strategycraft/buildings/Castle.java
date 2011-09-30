package net.lotrcraft.strategycraft.buildings;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Castle {
	private List<Building> buildings = new ArrayList<Building>();
	private Location l;
	private Citadel citadel;
	private Player player;
	private String playerName;
	
	
	public Castle (Location l, String playerName){
		this.l = l;
		this.player = Bukkit.getPlayerExact(playerName);
		this.playerName = playerName;
		citadel = new Citadel();
		citadel.build(l);
		createNewCastle();
	}
	
	public boolean getBuildingAtLoc(Location l){
		return false;
		
	}
	
	public boolean isBuildingLeft(){
		return !buildings.isEmpty();
	}

	private void createNewCastle() {
		buildings.add(citadel);
		
		
	}
	
	public void addBuilding(Building building){
		buildings.add(building);
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
