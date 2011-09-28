package net.lotrcraft.strategycraft.listeners;

import java.util.ArrayList;

import net.lotrcraft.strategycraft.Config;
import net.lotrcraft.strategycraft.buildings.Building;

import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.material.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class SignPlaced implements Listener {

	public void onSignChange(SignChangeEvent event) {

		String[] lines = event.getLines();
		Block block = event.getBlock();
		Sign sign = (Sign) block;
		Chunk chunk = block.getChunk();
		Location l = block.getLocation();
		Player player = event.getPlayer();
		Building building = new Building();

		int x = block.getX();
		int y = block.getY();
		int z = block.getZ();
		
		if (!lines[0].equals("[StrategyCraft]"))
			return;

		if (sign.isWallSign() && block.getRelative(sign.getAttachedFace()).getTypeId() == Config.coreBlock) {
			if (lines[1].equalsIgnoreCase("Castle")){
				if (Config.castles.get(player) != null) {
					event.setLine(1, ChatColor.RED + lines[0]);
					player.sendMessage("You already own a Castle!");
				} else {
					Config.createNewCastle(player, l);
					event.setLine(1, ChatColor.GREEN + lines[0]);
					player.sendMessage("Castle created!");
				}
			}
			else if ((building = Config.bldgs.get(lines[1])) == null){
				event.setLine(1, ChatColor.RED + lines[0]);
				player.sendMessage("Building doesn't exist!");
			} else{
				
			}

		}
	}
}
