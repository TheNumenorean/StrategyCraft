package net.lotrcraft.strategycraft.listeners;

import java.util.ArrayList;

import net.lotrcraft.strategycraft.Config;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.event.block.SignChangeEvent;

public class SignPlaced extends SignListener{
	
	public void onSignChange(SignChangeEvent event){
		

		
		String[] lines = event.getLines();
		Block block = event.getBlock();
		Sign sign = (Sign)block; 
		Chunk chunk = block.getChunk();
		
		int x = block.getX();
		int y = block.getY();
		int z = block.getZ();
		
		if (!lines[0].equals("[StrategyCraft]"))
			return;
		
		Block baseBlock;
		if (!(getBaseBlock(block, Config.coreBlock) == null)){

		}
	}

	public Block getBaseBlock(Block block, int id) {
		Block back;
		int x = block.getX();
		int y = block.getY();
		int z = block.getZ();
		if ((back =block.getChunk().getBlock(x - 1, y, z)).getTypeId() == id){
			return back;
		} else if ((back = block.getChunk().getBlock(x + 1, y, z)).getTypeId() == id){
			return back;
		} else if ((back = block.getChunk().getBlock(x, y + 1, z)).getTypeId() == id){
			return back;
		} else if ((back = block.getChunk().getBlock(x, y - 1, z)).getTypeId() == id){
			return back;
		}
		return null;
	}
}
