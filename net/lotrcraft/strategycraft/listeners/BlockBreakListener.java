package net.lotrcraft.strategycraft.listeners;

import java.util.List;

import net.lotrcraft.strategycraft.buildings.Building;
import net.lotrcraft.strategycraft.buildings.BuildingManager;
import net.lotrcraft.strategycraft.buildings.Castle;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockListener;

public class BlockBreakListener extends BlockListener {
	
	static List<Castle> castles;
	private Castle castle;
	
	public void onBlockBreak(BlockBreakEvent e){
		
		Player player = e.getPlayer();
		Building building;
		
		player.sendMessage("Called");
		if ((castle = BuildingManager.getCastleAtLoc(e.getBlock().getLocation())) != null){
			if (!castle.isBuildingLeft()){
				Bukkit.broadcastMessage(castle.getOwner() + "'s castle destroyed!");
				BuildingManager.destroyCastle(castle);
				
			}
		}else if ((building = BuildingManager.getBuildingAtLoc(e.getBlock().getLocation())) != null){
			building.getCastle().destroyBuilding(building);
		}

	}

}
