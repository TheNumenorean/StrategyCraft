package net.lotrcraft.strategycraft.listeners;

import java.util.Collection;
import java.util.List;

import net.lotrcraft.strategycraft.buildings.BuildingManager;
import net.lotrcraft.strategycraft.buildings.Castle;

import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockListener;

public class BlockBreakListener extends BlockListener {
	
	static List<Castle> castles;
	private Castle castle;
	
	public void onBlockBreak(BlockDamageEvent e){
		
		Player player = e.getPlayer();
		
		player.sendMessage("Called");
		if ((castle = BuildingManager.getCastleAtLoc(e.getBlock().getLocation())) != null){
			if (!castle.isBuildingLeft()){
				player.sendMessage(castle.getOwner() + "'s castle destroyed!");
				BuildingManager.destroyCastle(castle);
				
			}
		}else if (BuildingManager.getBuildingAtLoc(e.getBlock().getLocation()) != null){
			
		}

	}

}
