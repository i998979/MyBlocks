package to.epac.factorycraft.myblocks.gui;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import to.epac.factorycraft.myblocks.MyBlocks;
import to.epac.factorycraft.myblocks.config.MyBlocksConfig;

public class BlockChooserGui implements MyBlocksGui {
	
	private Plugin plugin = MyBlocks.inst();
	
	private int page = 1;
	
	public BlockChooserGui() {
		this(1);
	}
	public BlockChooserGui(int page) {
		this.page = page;
	}

	@Override
	public Inventory getInventory() {
		
		Inventory gui = Bukkit.createInventory(this, 54, ChatColor.DARK_BLUE + "MyBlocks Chooser");
		
		ArrayList<ItemStack> items = new ArrayList<>();
		
    	for (String block : MyBlocksConfig.getBlocks()) {
    		
    		ItemStack tool = MyBlocksConfig.getTool(block).clone();
    		ItemMeta meta = tool.getItemMeta();
    		
    		int blockId = MyBlocksConfig.getBlockId(block);
    		
    		List<String> lores = new ArrayList<String>();
    		if (tool.getItemMeta().getLore() != null)
    			lores = tool.getItemMeta().getLore();
    		
    		lores.add(ChatColor.BLUE + "---------------");
    		lores.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "Id: " + block);
    		lores.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "BlockId: " + blockId);
    		lores.add("");
    		lores.add(ChatColor.GRAY + "Left click to get");
    		lores.add(ChatColor.GRAY + "Right click to modify");
    		
    		meta.setLore(lores);
    		tool.setItemMeta(meta);
    		
    		items.add(tool);
    	}
    	
    	if (page > 1) gui.setItem(45, GuiButton.getPrevPage());
    	gui.setItem(49, GuiButton.getCurrPage(page, items.size() / 45 + 1));
		if (items.size() / 45 + 1 > page) gui.setItem(53, GuiButton.getNextPage());
    	
    	for (int i = 0; i < 45; i++) {
    		int index = page * 45 - 45 + i;
    		
    		if (index >= items.size()) break;
    		
    		ItemStack tool = items.get(page * 45 - 45 + i);
    		gui.setItem(i, tool);
    	}
    	
		return gui;
	}

	@Override
	public void onGuiClick(Player player, int slot, ItemStack clicked, ClickType clickType) {
		
		if (clicked == null || clicked.getType() == Material.AIR) return;
		
		if (slot < 45) {
			if (clickType == ClickType.LEFT) {
				
		    	player.getInventory().addItem(MyBlocksConfig.getTools().get(page * 45 - 45 + slot));
			}
			else if (clickType == ClickType.RIGHT) {
				
				player.openInventory(new ModifyGui(MyBlocksConfig.getBlocks().get(page * 45 - 45 + slot)).getInventory());
			}
		}
		else if (slot >= 45 && slot <= 54) {
			if (clicked.equals(GuiButton.getPrevPage()))
				player.openInventory(this.setPage(getPage() - 1).getInventory());
			else if (clicked.equals(GuiButton.getNextPage()))
				player.openInventory(this.setPage(getPage() + 1).getInventory());
		}
	}
	
	public BlockChooserGui setPage(int page) {
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
