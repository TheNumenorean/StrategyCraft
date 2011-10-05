package net.lotrcraft.strategycraft.buildings;

import org.bukkit.Location;

public abstract class Building {
	
	private static BuildingManager bm;
	
	Location location;
	
	public void build(Location l){
		Schematic s = getSchematic();
		byte[] bytes = s.getBlocks();
		
		location = new Location(l.getWorld(), s.getWidth()/2, l.getBlockY(), s.getLength()/2);
		
		for (int y = 0; y < s.getHeight(); y++){
			for (int x = 0; x < s.getWidth(); x++){
				for (int z = 0; z < s.getLength(); z++){
					l.getWorld().getBlockAt(location).setData(bytes[y + x + z]);
				}
			}
		}
		
	}
	
	public void destroy(){
		
	}
	
	abstract String getName();
	
	Schematic getSchematic(){
		return new Schematic(getClass().getClassLoader().getResourceAsStream(getName() + ".schematic"));
	}
	
	//TODO: does this work?
	BuildingManager getBuildingManager(){
		return bm;
	}


}
