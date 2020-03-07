package to.epac.factorycraft.myblocks.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import to.epac.factorycraft.myblocks.MyBlocks;

public class MyBlocksConfig {
	private static MyBlocks plugin = MyBlocks.inst();
	
	public enum SoundType {
		PLACE,
		BREAK,
		STEP,
		LEFT,
		LEFT_SNEAK,
		RIGHT,
		RIGHT_SNEAK;
	}
	
	
	
	public static void create(String id, int blockId) {
		plugin.getConfig().set("MyBlocks.Blocks." + id + ".Id", blockId);
		plugin.saveConfig();
	}
	public static void remove(String id) {
		plugin.getConfig().set("MyBlocks.Blocks." + id, null);
		plugin.saveConfig();
	}
	
	
	
	public static List<String> getBlocks() {
		List<String> blocks = new ArrayList<>();
		
		ConfigurationSection blockList = plugin.getConfig().getConfigurationSection("MyBlocks.Blocks");
		for (String id : blockList.getKeys(false)) {
			blocks.add(id);
		}
		return blocks;
	}
	
	
	
	public static String getSoundData(String id, SoundType soundType) {
		String data = "";
		
		switch (soundType) {
			case PLACE:
				data = plugin.getConfig().getString("MyBlocks.Blocks." + id + ".Sounds.Place");
				break;
			case BREAK:
				data = plugin.getConfig().getString("MyBlocks.Blocks." + id + ".Sounds.Break");
				break;
			case STEP:
				data = plugin.getConfig().getString("MyBlocks.Blocks." + id + ".Sounds.Step");
				break;
			case LEFT:
				data = plugin.getConfig().getString("MyBlocks.Blocks." + id + ".Sounds.Left");
				break;
			case LEFT_SNEAK:
				data = plugin.getConfig().getString("MyBlocks.Blocks." + id + ".Sounds.Left_Sneak");
				break;
			case RIGHT:
				data = plugin.getConfig().getString("MyBlocks.Blocks." + id + ".Sounds.Right");
				break;
			case RIGHT_SNEAK:
				data = plugin.getConfig().getString("MyBlocks.Blocks." + id + ".Sounds.Right_Sneak");
				break;
		}
		if (data.isEmpty()) return "";
		
		return data;
	}
	public static String getSound(String id, SoundType soundType) {
		return getSoundData(id, soundType).split("\\s", 4)[0];
	}
	public static String getMode(String id, SoundType soundType) {
		return getSoundData(id, soundType).split("\\s", 4)[1];
	}
	public static float getVolume(String id, SoundType soundType) {
		return Float.parseFloat(getSoundData(id, soundType).split("\\s", 4)[2]);
	}
	public static float getPitch(String id, SoundType soundType) {
		return Float.parseFloat(getSoundData(id, soundType).split("\\s", 4)[3]);
	}
	
	
	
	
	public static List<String> getLeftCommands(String id) {
		return plugin.getConfig().getStringList("MyBlocks.Blocks." + id + ".Commands.Left");
	}
	public static List<String> getLeftSneakCommands(String id) {
		return plugin.getConfig().getStringList("MyBlocks.Blocks." + id + ".Commands.Left_Sneak");
	}
	
	public static List<String> getRightCommands(String id) {
		return plugin.getConfig().getStringList("MyBlocks.Blocks." + id + ".Commands.Right");
	}
	public static List<String> getRightSneakCommands(String id) {
		return plugin.getConfig().getStringList("MyBlocks.Blocks." + id + ".Commands.Right_Sneak");
	}
	
	
	
	public static int getBlockId(String id) {
		return plugin.getConfig().getInt("MyBlocks.Blocks." + id + ".Id");
	}
	public static void setBlockId(String id, int blockId) {
		plugin.getConfig().set("MyBlocks.Blocks." + id + ".Id", blockId);
		plugin.saveConfig();
	}
	
	
	
	public static String getId(int blockId) {
		for (String id : plugin.getConfig().getConfigurationSection("MyBlocks.Blocks").getKeys(false)) {
			if (plugin.getConfig().getInt("MyBlocks.Blocks." + id + ".Id") == blockId)
				return id;
		}
		return null;
	}
	public static void setId(String from, String to) {
		ConfigurationSection section = plugin.getConfig().getConfigurationSection("MyBlocks.Blocks." + from);
		Map<String, Object> values = section.getValues(true);
		
		section.set("MyBlocks.Blocks." + from, null);
		plugin.getConfig().set("MyBlocks.Blocks." + to, values);
		plugin.saveConfig();
	}
	
	
	
	public static ItemStack getTool(String id) {
		return plugin.getConfig().getItemStack("MyBlocks.Blocks." + id + ".Tool");
	}
	public static void setTool(String id, ItemStack item) {
		plugin.getConfig().set("MyBlocks.Blocks." + id + ".Tool", item);
		plugin.saveConfig();
	}
	public static void removeTool(String id) {
		plugin.getConfig().set("MyBlocks.Blocks." + id + ".Tool", null);
		plugin.saveConfig();
	}
	public static ArrayList<ItemStack> getTools() {
		ArrayList<ItemStack> items = new ArrayList<>();
		
		for (String block : MyBlocksConfig.getBlocks()) {
			items.add(MyBlocksConfig.getTool(block).clone());
		}
		return items;
	}
	
	
	
	public static ArrayList<ItemStack> getLoots(String id) {
		ArrayList<ItemStack> lootList = (ArrayList<ItemStack>) plugin.getConfig().getList("MyBlocks.Blocks." + id + ".Loots");
		
		return lootList;
	}
	public static void addLoots(String id, ItemStack heldItem) {
		List<ItemStack> items = new ArrayList<>();

		if (getLoots(id) != null) {
			for (ItemStack item: getLoots(id)) {
				items.add(item);
			}
		}
		items.add(heldItem.clone());

		plugin.getConfig().set("MyBlocks.Blocks." + id + ".Loots", items);
		plugin.saveConfig();
	}
	public static void removeLoots(String id, ItemStack heldItem) {
		List<ItemStack> items = new ArrayList<>();

		if (getLoots(id) != null) {
			for (ItemStack item: getLoots(id)) {
				items.add(item);
			}
		}
		items.remove(heldItem.clone());
		
		plugin.getConfig().set("MyBlocks.Blocks." + id + ".Loots", items);
		plugin.saveConfig();
	}
	
	
	
	public static boolean isIdExist(String id) {
		ConfigurationSection blockList = plugin.getConfig().getConfigurationSection("MyBlocks.Blocks");
		
		for(String key : blockList.getKeys(false)) {
			if (id.equals(key))
				return true;
		}
		return false;
	}
	public static boolean isBlockIdExist(int blockId) {
		ConfigurationSection blockList = plugin.getConfig().getConfigurationSection("MyBlocks.Blocks");
		
		for(String key : blockList.getKeys(false)) {
			if (plugin.getConfig().getInt("MyBlocks.Blocks." + key + ".Id") == blockId)
				return true;
		}
		return false;
	}
}
