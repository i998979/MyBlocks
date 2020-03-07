package to.epac.factorycraft.myblocks.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public interface MyBlocksGui extends InventoryHolder {
	
	public void onGuiClick(Player player, int slot, ItemStack clicked, ClickType clickType);

}
