package net.lotrcraft.strategycraft.buildings;

import net.lotrcraft.strategycraft.StrategyCraft;
import net.lotrcraft.strategycraft.schematic.MissingSchematicError;
import net.lotrcraft.strategycraft.schematic.Schematic;

import org.bukkit.Location;
import org.bukkit.block.BlockFace;

public abstract class Building {
	
	
	private Castle castle;
	
	private Location location;
	
	/**
	 * Build this building at a given location. By default this will use 
	 * the supplied schematic to build.
	 * 
	 * @param l Location to build at
	 */
	public void build(Location l){
		Schematic s = getSchematic();
		byte[] bytes = s.getBlocks();
		location = l;
		
		
		/*
		if (blockFace == BlockFace.NORTH){
			
			location = new Location(l.getWorld(), l.getBlockX() - s.getWidth()/2, l.getBlockY(), l.getBlockZ() - s.getLength()/2);
			
		} else if (blockFace == BlockFace.EAST){
			
			location = new Location(l.getWorld(), l.getBlockX() + s.getWidth()/2, l.getBlockY(), l.getBlockZ() - s.getLength()/2);
			
		} else if (blockFace == BlockFace.WEST){
			
			location = new Location(l.getWorld(), l.getBlockX() - s.getWidth()/2, l.getBlockY(), l.getBlockZ() + s.getLength()/2);
			
		} else {
			
			location = new Location(l.getWorld(), l.getBlockX() + s.getWidth()/2, l.getBlockY(), l.getBlockZ() + s.getLength()/2);
			
		}
		*/

		
		for (int y = 0; y < s.getHeight(); y++){
			for (int x = 0; x < s.getWidth(); x++){
				for (int z = 0; z < s.getLength(); z++){
					
					Location tmpLoc = new Location(location.getWorld(), 
							location.getBlockX() - (s.getWidth()/2) + x,
							location.getBlockY() - (s.getHeight()/2) + y, 
							location.getBlockZ() - (s.getLength()/2) + z);
					
					l.getWorld().getBlockAt(tmpLoc).setData(bytes[y*s.getHeight() + x*s.getWidth() + z]);
				}
			}
		}
		
	}
	
	/**
	 * Destroys this building by setting all the previously created blocks to air.
	 */
	public void destroy(){
		//TODO: Doesnt work yet.
		Schematic s = getSchematic();
		for (int y = 0; y < s.getHeight(); y++){
			for (int x = 0; x < s.getWidth(); x++){
				for (int z = 0; z < s.getLength(); z++){
					location.getWorld().getBlockAt(location).setTypeId(1);
				}
			}
		}
	}
	
	/**
	 * Gets the name of this Building
	 * @return The building name
	 */
	public String getName(){
		String s = this.getClass().getName();
		return s.substring(s.lastIndexOf(".") + 1);
	}
	
	/**
	 * Gets this building's schematic. This will look inside the building's jar for a schematic labeled <buildingname>.schematic
	 * @return The Schematic, or null if it isnt found.
	 */
	public Schematic getSchematic(){
		try {
			return new Schematic(getClass().getClassLoader().getResourceAsStream(getName() + ".schematic"));
		} catch (MissingSchematicError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Gets the local building manager.
	 * @return A new instance of BuildingManager
	 */
	public BuildingManager getBuildingManager(){
		BuildingManager bm = new BuildingManager();
		return bm;
	}

	/**
	 * Gets the castle that this building is crrently in.
	 * @return The castle
	 */
	public Castle getCastle() {
		return castle;
		
	}

	/**
	 * Sets this building's castle.
	 * @param cstl The castle
	 */
	public void setCastle(Castle cstl) {
		this.castle = cstl;
	}
	
	/**
	 * Gets the location of the center blcok of this building.
	 * @return The location
	 */
	public Location getLocation(){
		return location;
	}


}
