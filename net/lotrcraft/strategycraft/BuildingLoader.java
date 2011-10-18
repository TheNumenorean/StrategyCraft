package net.lotrcraft.strategycraft;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.util.config.Configuration;
import org.yaml.snakeyaml.Yaml;

import net.lotrcraft.strategycraft.buildings.Building;
import net.lotrcraft.strategycraft.units.Unit;

public class BuildingLoader {
	public static Class<? extends Building> loadBuilding(File file) {
		try {
			JarFile jf = new JarFile(file);
			JarEntry je = jf.getJarEntry("building.yml");
			BufferedReader br = new BufferedReader(new InputStreamReader(jf.getInputStream(je)));
			String mainclass = br.readLine();
			br.close();
			jf.close();
			return new URLClassLoader(new URL[] { file.toURI().toURL() }).loadClass(mainclass).asSubclass(Building.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@SuppressWarnings("deprecation")
	public static Class<? extends Unit> loadUnit(File file) {
		try {
			JarFile jf = new JarFile(file);
			JarEntry je = jf.getJarEntry("building.yml");
			BufferedReader br = new BufferedReader(new InputStreamReader(jf.getInputStream(je)));
			String mainclass = br.readLine();
			FileConfiguration c = new FileConfiguration();
			Yaml y = new Yaml();
			br.close();
			jf.close();
			return new URLClassLoader(new URL[] { file.toURI().toURL() }).loadClass(mainclass).asSubclass(Building.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}