package to.epac.factorycraft.myblocks.gui;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import to.epac.factorycraft.myblocks.config.MyBlocksConfig;

public class LootsGui implements MyBlocksGui {
	
	private String lootsId;
	private int page;
	
	public LootsGui(String lootsId) {
		this.lootsId = lootsId;
		this.page = 1;
	}
	public LootsGui(String lootsId, int page) {
		this.lootsId = lootsId;
		this.page = page;
	}

	@Override
	public Inventory getInventory() {
		
		Inventory gui = Bukkit.createInventory(this, 54,
				ChatColor.DARK_AQUA + "MyBlocks Loots Modifier - " + lootsId);
		
		ArrayList<ItemStack> items = MyBlocksConfig.getLoots(lootsId);
		
		if (page == 1) gui.setItem(45, GuiButton.getReturn());
    	if (page > 1) gui.setItem(45, GuiButton.getPrevPage());
    	gui.setItem(49, GuiButton.getCurrPage(page));
    	
    	if (items != null) {
    		if (items.size() / 45 + 1 > page) gui.setItem(53, GuiButton.getNextPage());
    		
    		ItemStack info = new ItemStack(Material.BOOK);
    		ItemMeta infoMeta = info.getItemMeta();
    		infoMeta.setDisplayName(ChatColor.GREEN + "Information");
    		infoMeta.setLore(Arrays.asList(
    				ChatColor.GRAY + "Click the items above",
    				ChatColor.GRAY + "to remove from Loots"));
    		info.setItemMeta(infoMeta);
    		gui.setItem(47, info);
    		
        	for (int i = 0; i < 45; i++) {
        		int index = page * 45 - 45 + i;
        		
        		if (index >= items.size()) break;
        		
        		ItemStack loot = items.get(page * 45 - 45 + i);
        		gui.setItem(i, loot);
        	}
    	}
    	else
    		gui.setItem(22, GuiButton.getEmpty(
				ChatColor.GRAY + "Add loots by typing",
				ChatColor.GRAY + "\"/mb addLoots " + lootsId + "\""));
    	
		return gui;
	}

	@Override
	public void onGuiClick(Player player, int slot, ItemStack clicked, ClickType clickType) {
		
		if (clicked == null || clicked.getType() == Material.AIR) return;
		
		if (slot < 45) {
			if (clicked.equals(GuiButton.getEmpty(
					ChatColor.GRAY + "Add loots by typing",
					ChatColor.GRAY + "\"/mb addLoots " + lootsId + "\""))) return;
			
			int index = page * 45 - 45 + slot;
			MyBlocksConfig.removeLoots(lootsId, MyBlocksConfig.getLoots(lootsId).get(index));
			player.openInventory(this.getInventory());
		}
		
		else if (slot >= 45 && slot <= 54) {
			if (clicked.equals(GuiButton.getPrevPage()))
				player.openInventory(this.setPage(getPage() - 1).getInventory());
			else if (clicked.equals(GuiButton.getNextPage()))
				player.openInventory(this.setPage(getPage() + 1).getInventory());
			else if (clicked.equals(GuiButton.getReturn()))
				player.openInventory(new ModifyGui(lootsId).getInventory());
		}
	}
	
	public LootsGui setPage(int page) {
        if(page < 1)
        	this.page = 1;
        else
        	this.page = page;
        return this;
    }
    public int getPage() {
        return page;
    }
}
