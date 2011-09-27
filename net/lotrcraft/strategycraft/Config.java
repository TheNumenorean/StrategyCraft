package net.lotrcraft.strategycraft;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.util.config.Configuration;

public class Config {
	
	public static int coreBlock;

	public static void loadConf(Configuration config) {
		coreBlock =  getInt("coreBlock", 49);
		
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
