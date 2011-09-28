package net.lotrcraft.strategycraft.buildings;

import java.io.File;
import java.io.FileInputStream;

public class Test {
	public static void main(String[] args) {
		try {
			Schematic house = new Schematic(new FileInputStream(new File(
					"house.schematic")));
			System.out.println("Size: (" + house.getWidth() + ", "
					+ house.getHeight() + ", " + house.getLength());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}