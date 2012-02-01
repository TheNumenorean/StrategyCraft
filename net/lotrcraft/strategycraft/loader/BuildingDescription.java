package net.lotrcraft.strategycraft.loader;

import net.lotrcraft.strategycraft.buildings.Building;
import net.lotrcraft.strategycraft.units.Unit;

public class BuildingDescription {

	private Class<? extends Building> building;
	private Class<? extends Unit> unit;
	private String name;
	
	/**
	 * Creates a new BuildingDescription with the three given parameters.
	 * @param name The name of the building
	 * @param building Class The class representing the actual building
	 * @param unitClass The class representing this building's unit.
	 */
	public BuildingDescription(String name, Class<? extends Building> buildingClass, Class<? extends Unit> unitClass) {
		this.name = name;
		this.building = buildingClass;
		this.unit = unitClass;
	}
	
	/**
	 * Gets the building class for this building
	 * @return The building
	 */
	public Class<? extends Building> getBuilding(){
		return building;
	}

	/**
	 * Gets the unit class for this building
	 * @return The class
	 */
	public Class<? extends Unit> getUnit(){
		return unit;
	}
	
	/**
	 * Gets the name of this building.
	 * @return The name
	 */
	public String getName(){
		return name;
	}
}
