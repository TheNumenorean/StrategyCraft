package net.lotrcraft.strategycraft.buildings;

import org.bukkit.Location;

public abstract class Building {
	
	void build(Location l){
		int x, y, z;
		Schematic s = getSchematic();
		byte[] bytes = s.getBlocks();
		
		for (int y = 0; y < s.getHeight(); y++){
			l.getWorld().getBlockAt(bytes.)
		}
		
	}
	
	abstract String getName();
	
	Schematic getSchematic(){
		return new Schematic(getClass().getClassLoader().getResourceAsStream(getName + ".schematic"));
	}


}
