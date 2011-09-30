package net.lotrcraft.strategycraft.listeners;

import java.util.Collection;
import java.util.List;

import net.lotrcraft.strategycraft.buildings.BuildingManager;
import net.lotrcraft.strategycraft.buildings.Castle;

import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockListener;

public class BlockBreakListener extends BlockListener {
	
	static List<Castle> castles;
	private Castle castle;
	
	public void onBlockBreak(BlockDamageEvent e){
		if ((castle = BuildingManager.getCastleAtLoc(e.getBlock().getLocation())) != null){
			if (!castle.isBuildingLeft()){
				BuildingManager.destroyCastle(castle);
			}
		}else if (BuildingManager.getBuildingAtLoc(e.getBlock().getLocation()) != null){
			
		}

	}

}
