package net.lotrcraft.strategycraft.schematic;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import org.bukkit.util.BlockVector;
import org.bukkit.util.Vector;
import org.jnbt.ByteArrayTag;
import org.jnbt.ByteTag;
import org.jnbt.CompoundTag;
import org.jnbt.FloatTag;
import org.jnbt.IntTag;
import org.jnbt.ListTag;
import org.jnbt.NBTInputStream;
import org.jnbt.ShortTag;
import org.jnbt.StringTag;
import org.jnbt.Tag;

public class Schematic {
	
	
	//This function was taken from WorldEdit as a prototype for a schematic reader personalised for StrategyCraft,
	// any of this code yet. once i have added my own code i will mark the places that remain from the WE code.
	
	/**
	 * 
	 * @param path
	 * @return
	 * @throws SchematicReadException
	 * @throws IOException
	 */
    public static Schematic loadSchematic(File path)
            throws SchematicReadException, IOException {
        FileInputStream stream = new FileInputStream(path);
        NBTInputStream nbtStream = new NBTInputStream(
                new GZIPInputStream(stream));

        Vector origin = new Vector();
        Vector offset = new Vector();

        // Schematic tag
        CompoundTag schematicTag = (CompoundTag) nbtStream.readTag();
        if (!schematicTag.getName().equals("Schematic")) {
            throw new SchematicReadException("Tag \"Schematic\" does not exist or is not first");
        }

        // Check
        Map<String, Tag> schematic = schematicTag.getValue();
        if (!schematic.containsKey("Blocks")) {
            throw new SchematicReadException("Schematic file is missing a \"Blocks\" tag");
        }

        // Get information
        short width = (Short) getChildTag(schematic, "Width", ShortTag.class).getValue();
        short length = (Short) getChildTag(schematic, "Length", ShortTag.class).getValue();
        short height = (Short) getChildTag(schematic, "Height", ShortTag.class).getValue();

        
        /*Unneedeed
        try {
            int originX = (Integer) getChildTag(schematic, "WEOriginX", IntTag.class).getValue();
            int originY = (Integer) getChildTag(schematic, "WEOriginY", IntTag.class).getValue();
            int originZ = (Integer) getChildTag(schematic, "WEOriginZ", IntTag.class).getValue();
            origin = new Vector(originX, originY, originZ);
        } catch (SchematicReadException e) {
            // No origin data
        }

        try {
            int offsetX = (Integer) getChildTag(schematic, "WEOffsetX", IntTag.class).getValue();
            int offsetY = (Integer) getChildTag(schematic, "WEOffsetY", IntTag.class).getValue();
            int offsetZ = (Integer) getChildTag(schematic, "WEOffsetZ", IntTag.class).getValue();
            offset = new Vector(offsetX, offsetY, offsetZ);
        } catch (SchematicReadException e) {
            // No offset data
        }
        */

        // Check type of Schematic
        String materials = (String) getChildTag(schematic, "Materials", StringTag.class).getValue();
        if (!materials.equals("Alpha")) {
            throw new SchematicReadException("Schematic file is not an Alpha schematic");
        }

        // Get blocks
        byte[] blocks = (byte[]) getChildTag(schematic, "Blocks", ByteArrayTag.class).getValue();
        byte[] blockData = (byte[]) getChildTag(schematic, "Data", ByteArrayTag.class).getValue();

        // Need to pull out tile entities
        List<Tag> tileEntities = (List<Tag>) ((ListTag) getChildTag(schematic, "TileEntities", ListTag.class))
                .getValue();
        Map<BlockVector, Map<String, Tag>> tileEntitiesMap =
                new HashMap<BlockVector, Map<String, Tag>>();

        for (Tag tag : tileEntities) {
            if (!(tag instanceof CompoundTag)) continue;
            CompoundTag t = (CompoundTag) tag;

            int x = 0;
            int y = 0;
            int z = 0;

            Map<String, Tag> values = new HashMap<String, Tag>();

            for (Map.Entry<String, Tag> entry : t.getValue().entrySet()) {
                if (entry.getKey().equals("x")) {
                    if (entry.getValue() instanceof IntTag) {
                        x = ((IntTag) entry.getValue()).getValue();
                    }
                } else if (entry.getKey().equals("y")) {
                    if (entry.getValue() instanceof IntTag) {
                        y = ((IntTag) entry.getValue()).getValue();
                    }
                } else if (entry.getKey().equals("z")) {
                    if (entry.getValue() instanceof IntTag) {
                        z = ((IntTag) entry.getValue()).getValue();
                    }
                }

                values.put(entry.getKey(), entry.getValue());
            }

            BlockVector vec = new BlockVector(x, y, z);
            tileEntitiesMap.put(vec, values);
        }

        Vector size = new Vector(width, height, length);
        CuboidClipboard clipboard = new CuboidClipboard(size);
        clipboard.setOrigin(origin);
        clipboard.setOffset(offset);

        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                for (int z = 0; z < length; ++z) {
                    int index = y * width * length + z * width + x;
                    BlockVector pt = new BlockVector(x, y, z);
                    BaseBlock block;

                    switch (blocks[index]) {
                    case BlockID.WALL_SIGN:
                    case BlockID.SIGN_POST:
                        block = new SignBlock(blocks[index], blockData[index]);
                        break;

                    case BlockID.CHEST:
                        block = new ChestBlock(blockData[index]);
                        break;

                    case BlockID.FURNACE:
                    case BlockID.BURNING_FURNACE:
                        block = new FurnaceBlock(blocks[index], blockData[index]);
                        break;

                    case BlockID.DISPENSER:
                        block = new DispenserBlock(blockData[index]);
                        break;

                    case BlockID.MOB_SPAWNER:
                        block = new MobSpawnerBlock(blockData[index]);
                        break;

                    case BlockID.NOTE_BLOCK:
                        block = new NoteBlock(blockData[index]);
                        break;

                    default:
                        block = new BaseBlock(blocks[index], blockData[index]);
                        break;
                    }

                    if (block instanceof TileEntityBlock
                            && tileEntitiesMap.containsKey(pt)) {
                        ((TileEntityBlock) block).fromTileEntityNBT(
                                tileEntitiesMap.get(pt));
                    }

                    clipboard.data[x][y][z] = block;
                }
            }
        }

        return clipboard;
    }	
    
    private static Tag getChildTag(Map<String, Tag> items, String key,
            Class<? extends Tag> expected) throws SchematicReadException {

        if (!items.containsKey(key)) {
            throw new SchematicReadException("Schematic file is missing a \"" + key + "\" tag");
        }
        Tag tag = items.get(key);
        if (!expected.isInstance(tag)) {
            throw new SchematicReadException(
                key + " tag is not of tag type " + expected.getName());
        }
        return tag;
    }

    
	/*
	 * The OLD Schematic reader, written by BR_
	Map<String, Tag> schematic = null;

	public Schematic(InputStream f) throws MissingSchematicError {
		
		if (f== null)
			throw new MissingSchematicError("Invalid Schematic Input Stream");
		
		try {
			schematic = ((CompoundTag) new NBTInputStream(f).readTag())
					.getValue();
		} catch (Exception e) {
			throw new MissingSchematicError("Can't find schematic " + f + " !");
		}
	}

	public short getWidth() {
		return ((ShortTag) schematic.get("Width")).getValue();
	}

	public short getLength() {
		return ((ShortTag) schematic.get("Length")).getValue();
	}

	public short getHeight() {
		return ((ShortTag) schematic.get("Height")).getValue();
	}

	public byte[] getBlocks() {
		return ((ByteArrayTag) schematic.get("Blocks")).getValue();
	}

	public byte[] getData() {
		return ((ByteArrayTag) schematic.get("Data")).getValue();
	}

	public List<Sign> getSigns() {
		List<Sign> signs = new ArrayList<Sign>();
		for (Tag tileEntity : ((ListTag) schematic.get("TileEntities"))
				.getValue()) {
			Map<String, Tag> te = ((CompoundTag) tileEntity).getValue();
			String id = ((StringTag) te.get("id")).getValue();
			if (id.equals("Sign")) {
				Sign s = new Sign();
				s.x = ((IntTag) te.get("x")).getValue();
				s.y = ((IntTag) te.get("y")).getValue();
				s.z = ((IntTag) te.get("z")).getValue();
				s.text = new String[] {
						((StringTag) te.get("Text1")).getValue(),
						((StringTag) te.get("Text2")).getValue(),
						((StringTag) te.get("Text3")).getValue(),
						((StringTag) te.get("Text4")).getValue() };
				signs.add(s);
			}
		}
		return signs;
	}

	public List<NoteBlock> getNoteBlocks() {
		List<NoteBlock> noteBlocks = new ArrayList<NoteBlock>();
		for (Tag tileEntity : ((ListTag) schematic.get("TileEntities"))
				.getValue()) {
			Map<String, Tag> te = ((CompoundTag) tileEntity).getValue();
			String id = ((StringTag) te.get("id")).getValue();
			if (id.equals("Music")) {
				NoteBlock note = new NoteBlock();
				note.x = ((IntTag) te.get("x")).getValue();
				note.y = ((IntTag) te.get("y")).getValue();
				note.z = ((IntTag) te.get("z")).getValue();
				note.pitch = ((ByteTag) te.get("note")).getValue();
				noteBlocks.add(note);
			}
		}
		return noteBlocks;
	}

	public List<Piston> getPistons() {
		List<Piston> pistons = new ArrayList<Piston>();
		for (Tag tileEntity : ((ListTag) schematic.get("TileEntities"))
				.getValue()) {
			Map<String, Tag> te = ((CompoundTag) tileEntity).getValue();
			String id = ((StringTag) te.get("id")).getValue();
			if (id.equals("Piston")) {
				Piston piston = new Piston();
				piston.x = ((IntTag) te.get("x")).getValue();
				piston.y = ((IntTag) te.get("y")).getValue();
				piston.z = ((IntTag) te.get("z")).getValue();
				piston.blockId = ((IntTag) te.get("blockId")).getValue();
				piston.blockData = ((IntTag) te.get("blockData")).getValue();
				piston.facing = ((IntTag) te.get("facing")).getValue();
				piston.progress = ((FloatTag) te.get("progress")).getValue();
				piston.extending = ((ByteTag) te.get("extending")).getValue();
				pistons.add(piston);
			}
		}
		return pistons;
	}

	public static class Sign {
		int x, y, z;
		String text[];
	}

	public static class NoteBlock {
		int x, y, z;
		byte pitch;
	}

	public static class Piston {
		int x, y, z, blockId, blockData, facing;
		float progress;
		byte extending;
	}
	*/
}