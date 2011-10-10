package net.lotrcraft.strategycraft.buildings;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
	Map<String, Tag> schematic = null;

	public Schematic(InputStream f) {
		try {
			schematic = ((CompoundTag) new NBTInputStream(f).readTag())
					.getValue();
		} catch (Exception e) {
			e.printStackTrace();
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
}