package net.lotrcraft.strategycraft.buildings;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;

public class Castle {
	List<Building> buildings = new ArrayList();
	Location l;
	Citadel citadel;
	
	
	public Castle (Location l){
		this.l = l;
		citadel = new Citadel();
		citadel.build(l);
		createNewCastle();
	}
	
	public Castle(){
		
	}


	private void createNewCastle() {
		buildings.add(citadel);
		
		
	}
	
	void addBuilding(Building building){
		buildings.add(building);
	}
	

}