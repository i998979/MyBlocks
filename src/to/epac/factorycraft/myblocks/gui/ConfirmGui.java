package to.epac.factorycraft.myblocks.gui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import to.epac.factorycraft.myblocks.config.MyBlocksConfig;

public class ConfirmGui implements MyBlocksGui {
	
	private String removingId;
	
	public ConfirmGui(String removingId) {
		this.removingId = removingId;
	}
	
	@Override
	public Inventory getInventory() {
		
		Inventory gui = Bukkit.createInventory(this, 27,
				ChatColor.DARK_RED + "MyBlocks Removal - " + removingId);
		
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 9; col++) {
				if (col < 3) gui.setItem(row * 9 + col, GuiButton.getConfirm());
				if (col > 5) gui.setItem(row * 9 + col, GuiButton.getCancel());
			}
		}
		gui.setItem(13, MyBlocksConfig.getTool(removingId));
		
		return gui;
	}

	@Override
	public void onGuiClick(Player player, int slot, ItemStack clicked, ClickType clickType) {
		
		if (clicked == null || clicked.getType() == Material.AIR) return;
		
		if (slot != 13) {
			if (clicked.equals(GuiButton.getConfirm())) {
				MyBlocksConfig.remove(removingId);
				player.openInventory(new BlockChooserGui(1).getInventory());
			}
			
			else if (clicked.equals(GuiButton.getCancel()))
				player.openInventory(new ModifyGui(removingId).getInventory());
		}		
	}
}
