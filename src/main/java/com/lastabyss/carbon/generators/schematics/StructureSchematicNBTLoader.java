package com.lastabyss.carbon.generators.schematics;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import net.minecraft.server.v1_8_R3.MinecraftKey;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.NBTCompressedStreamTools;
import net.minecraft.server.v1_8_R3.NBTTagCompound;

import org.apache.commons.io.IOUtils;

import com.google.common.collect.Maps;
import com.lastabyss.carbon.Carbon;

public class StructureSchematicNBTLoader {

	private final Map<String, StructureSchmeatic> schematics = Maps.newHashMap();

	public StructureSchmeatic getSchematic(MinecraftKey key) {
		String name = key.a();
		if (this.schematics.containsKey(name)) {
			return this.schematics.get(name);
		} else {
			this.loadSchematic(key);
			if (this.schematics.containsKey(name)) {
				return this.schematics.get(name);
			} else {
				StructureSchmeatic var3 = new StructureSchmeatic();
				this.schematics.put(name, var3);
				return var3;
			}
		}
	}

	public boolean loadSchematic(MinecraftKey key) {
		String name = key.a();
		File folder = MinecraftServer.getServer().d("structures");
		File file = new File(folder, name + ".nbt");
		if (!file.exists()) {
			return this.loadSchematic0(key);
		} else {
			FileInputStream is = null;
			boolean success;
			try {
				is = new FileInputStream(file);
				this.loadSchematicInputStream(name, is);
				return true;
			} catch (Throwable var11) {
				success = false;
			} finally {
				IOUtils.closeQuietly(is);
			}
			return success;
		}
	}

	private boolean loadSchematic0(MinecraftKey var1) {
		String namespace = "minecraft";
		String name = var1.a();
		InputStream is = null;

		boolean var6;
		try {
			is = Carbon.class.getResourceAsStream("/assets/" + namespace + "/structures/" + name + ".nbt");
			this.loadSchematicInputStream(name, is);
			return true;
		} catch (Throwable var10) {
			var6 = false;
		} finally {
			IOUtils.closeQuietly(is);
		}

		return var6;
	}

	private void loadSchematicInputStream(String name, InputStream is) throws IOException {
		NBTTagCompound compound = NBTCompressedStreamTools.a(is);
		StructureSchmeatic schematic = new StructureSchmeatic();
		schematic.load(compound);
		this.schematics.put(name, schematic);
	}

	public boolean saveSchematic(MinecraftKey var1) {
		String name = var1.a();
		if (!this.schematics.containsKey(name)) {
			return false;
		} else {
			File folder = MinecraftServer.getServer().d("structures");
			if (!folder.exists()) {
				if (!folder.mkdirs()) {
					return false;
				}
			} else if (!folder.isDirectory()) {
				return false;
			}

			File file = new File(folder, name + ".nbt");
			NBTTagCompound compound = new NBTTagCompound();
			StructureSchmeatic schematic = this.schematics.get(name);
			FileOutputStream os = null;

			boolean success;
			try {
				schematic.save(compound);
				os = new FileOutputStream(file);
				NBTCompressedStreamTools.a(compound, os);
				return true;
			} catch (Throwable var13) {
				success = false;
			} finally {
				IOUtils.closeQuietly(os);
			}

			return success;
		}
	}

}
