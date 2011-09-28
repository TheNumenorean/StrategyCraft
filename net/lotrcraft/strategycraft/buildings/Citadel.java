package net.lotrcraft.strategycraft.buildings;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

public class Citadel extends Building {
	Material material = Material.STONE;
	Chunk chunk;
	World world;
	Location l;
	Block block;
	int x, y, z;
	
	void build(Location l){
		this.l = l;
		block = l.getBlock();
		x = block.getX();
		y = block.getY();
		z = block.getZ();
		world = l.getWorld();
		chunk = l.getBlock().getChunk();
		world.getBlockAt(x + 2, y, z).setType(this.material);
		world.getBlockAt(x + 2, y, z + 1).setType(this.material);
		world.getBlockAt(x + 2, y, z + 2).setType(this.material);
		world.getBlockAt(x + 1, y, z + 2).setType(this.material);
		world.getBlockAt(x, y, z + 2).setType(this.material);
		world.getBlockAt(x - 1, y, z + 2).setType(this.material);
		world.getBlockAt(x - 2, y, z + 2).setType(this.material);
		world.getBlockAt(x - 2, y, z + 1).setType(this.material);
		world.getBlockAt(x - 2, y, z).setType(this.material);
		world.getBlockAt(x + 2, y, z - 1).setType(this.material);
		world.getBlockAt(x + 2, y, z - 2).setType(this.material);
		world.getBlockAt(x + 1, y, z - 2).setType(this.material);
		world.getBlockAt(x, y, z - 2).setType(this.material);
		world.getBlockAt(x - 1, y, z - 2).setType(this.material);
		world.getBlockAt(x - 2, y, z - 2).setType(this.material);
		world.getBlockAt(x - 2, y, z - 1).setType(this.material);
	}
	
	void destroy(){
		
	}
	
	

}
