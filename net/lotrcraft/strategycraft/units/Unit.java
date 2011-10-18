package net.lotrcraft.strategycraft.units;

import net.lotrcraft.strategycraft.buildings.Castle;

import org.bukkit.Location;

public abstract class Unit {
	
	Castle castle;
	
	public Unit(Castle castle){
		this.castle = castle;
	}
	
	Castle getCastle(){
		return castle;
	}
	abstract void onCreate(Location l);
	abstract void onDestroy();
	abstract void onMove(Location l);
	abstract void onAttack(Object victim);
	abstract void onAttacked(Object attacker);

}
