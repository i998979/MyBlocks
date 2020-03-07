package to.epac.factorycraft.myblocks.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GuiHandler implements Listener {
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		
		if (event.getInventory().getHolder() instanceof MyBlocksGui) {
			event.setCancelled(true);
			
			MyBlocksGui gui = (MyBlocksGui) event.getInventory().getHolder();
			gui.onGuiClick((Player) event.getWhoClicked(), event.getRawSlot(), event.getCurrentItem(), event.getClick());
		}
	}
}
