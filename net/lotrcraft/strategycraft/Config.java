package net.lotrcraft.strategycraft;

import java.util.Map;
import java.util.TreeMap;

import net.lotrcraft.strategycraft.buildings.Building;
import net.lotrcraft.strategycraft.buildings.Castle;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.config.Configuration;
import org.bukkit.util.config.ConfigurationNode;

public class Config {
	
	public static int coreBlock;
	public static Map<String, Castle> castles = new TreeMap<String, Castle>();
	public static Map<String, Building> bldgs = new TreeMap<String, Building>();
	static Building tmpB;
	
	public static void createNewCastle(String name, Location l){
		castles.put(name, new Castle(l));
	}

	public static void loadConf() {
		coreBlock =  getInt("coreBlock", 49);
		
		tmpB = new Building();
		bldgs.put("Castle", tmpB);
		
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
