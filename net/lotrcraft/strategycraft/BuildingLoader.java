package net.lotrcraft.strategycraft;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.yaml.snakeyaml.Yaml;

import net.lotrcraft.strategycraft.buildings.Building;
import net.lotrcraft.strategycraft.buildings.BuildingDescription;
import net.lotrcraft.strategycraft.buildings.InvalidBuildingConfException;
import net.lotrcraft.strategycraft.units.Unit;

public class BuildingLoader {
	private static Yaml yaml;

	@SuppressWarnings("unchecked")
	public static BuildingDescription loadBuilding(File file) throws IOException, InvalidBuildingConfException {
		
		String building = null, unit = null, name = null;
		BuildingDescription d;
			JarFile jf = new JarFile(file);
			JarEntry je = jf.getJarEntry("building.yml");
			Map<String, Object> map = (Map<String, Object>) yaml.load(jf.getInputStream(je));
			
			try {
				building = (String) map.get("building");
				unit = (String) map.get("unit");
				name = (String) map.get("name");
				
				jf.close();
				
				d = new BuildingDescription(name, new URLClassLoader(new URL[] { file.toURI().toURL() }).loadClass(building).asSubclass(Building.class), new URLClassLoader(new URL[] { file.toURI().toURL() }).loadClass(unit).asSubclass(Unit.class));
				
			} catch (NullPointerException e){
				throw new InvalidBuildingConfException();
			} catch (ClassNotFoundException e) {
				throw new InvalidBuildingConfException();
			}
			return d;
	}
}