package net.lotrcraft.strategycraft.units;

import net.lotrcraft.strategycraft.buildings.Castle;

import org.bukkit.Location;

public abstract class Unit {
	
	Castle castle;
	
	public Unit(Castle castle, Location l){
		this.castle = castle;
		create(l);
	}
	
	public Castle getCastle(){
		return castle;
	}
	
	public abstract void create(Location l);
	public abstract void onDestroy();
	public abstract void onMove(Location l);
	public abstract void onAttack(Object victim);
	public abstract void onAttacked(Object attacker);

}
