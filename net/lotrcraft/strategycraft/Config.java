package net.lotrcraft.strategycraft;

import java.util.Map;
import java.util.TreeMap;

import net.lotrcraft.strategycraft.buildings.Building;
import net.lotrcraft.strategycraft.buildings.BuildingManager;
import net.lotrcraft.strategycraft.buildings.Castle;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.config.Configuration;
import org.bukkit.util.config.ConfigurationNode;

public class Config {
	
	public static int coreBlock;

	static Building tmpB;

	public static void loadConf() {
		coreBlock =  getInt("coreBlock", 49);
		
		tmpB = new Building();
		tmpC = new Castle();
		BuildingManager.addBuildingToCastle(tmpC, tmpB);
		
		Location location = new Location(null, coreBlock, coreBlock, coreBlock);
		
	}
	
	public Object getProperty(String path, Object def) {
		if(isNull(path))
			return setProperty(path, def);
		return Main.config.getProperty(path);
	}

	public static int getInt(String path, Integer def) {
		if(isNull(path))
			return (Integer) setProperty(path, def);
		return Main.config.getInt(path, def);
	}

	public static Boolean getBoolean(String path, Boolean def) {
		if(isNull(path))
			return (Boolean) setProperty(path, def);
		return Main.config.getBoolean(path, def);
	}

	private static Object setProperty(String path, Object val) {
		Main.config.setProperty(path, val);
		return val;
	}

	private static boolean isNull(String path) {
		return Main.config.getProperty(path) == null;
	}
}
