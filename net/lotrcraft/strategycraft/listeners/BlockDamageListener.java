package net.lotrcraft.strategycraft.listeners;

import java.util.Collection;

import net.lotrcraft.strategycraft.buildings.BuildingManager;
import net.lotrcraft.strategycraft.buildings.Castle;

import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockListener;

public class BlockDamageListener extends BlockListener {
	
	static Castle[] castles;
	private Castle castle;
	
	public void onBlockDamage(BlockDamageEvent e){
		if ((castle = BuildingManager.getCastleAtLoc(e.getBlock().getLocation())) != null){
			if (!castle.isBuildingLeft()){
				castle.destroy();
			}
		}else if (BuildingManager.getBuildingAtLoc(e.getBlock().getLocation()) != null){
			
		}

	}

}
