package net.lotrcraft.strategycraft.units;

import org.bukkit.Location;

public abstract class Unit {
	
	abstract void onCreate(Location l);
	abstract void onDestroy(Location l);
	abstract void onMove(Location l);
	abstract void onAttack(Object o);

}
