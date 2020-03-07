package to.epac.factorycraft.myblocks.guis;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class MyBlocksHolder implements InventoryHolder {
	
	private MyBlocksGUI myblocksgui;
	
	MyBlocksHolder(MyBlocksGUI myblocksgui) {
		this.myblocksgui = myblocksgui;
	}
	
	MyBlocksGUI getMyBlocksGUI() {
		return myblocksgui;
	}
	
	@Override
	public Inventory getInventory() {
		return null;
	}
}
