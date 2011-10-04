package net.lotrcraft.strategycraft.buildings;

import org.bukkit.Location;

public abstract class Building {
	
	void build(Location l){
		Schematic s = getSchematic();
		byte[] bytes = s.getBlocks();
		
		Location corner = new Location(l.getWorld(), s.getWidth()/2, l.getBlockY(), s.getLength()/2);
		
		for (int y = 0; y < s.getHeight(); y++){
			for (int x = 0; x < s.getWidth(); x++){
				for (int z = 0; z < s.getLength(); z++){
					l.getWorld().getBlockAt(corner).setData(bytes[y][x][z]);
				}
			}
		}
		
	}
	
	abstract String getName();
	
	Schematic getSchematic(){
		return new Schematic(getClass().getClassLoader().getResourceAsStream(getName + ".schematic"));
	}


}
