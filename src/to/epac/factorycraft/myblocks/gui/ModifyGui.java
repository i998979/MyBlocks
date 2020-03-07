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

import to.epac.factorycraft.myblocks.config.MyBlocksConfig;
import to.epac.factorycraft.myblocks.guis.sign.SignGUI;
import to.epac.factorycraft.myblocks.utils.Utils;

public class ModifyGui implements MyBlocksGui {
	
	private String modifyingId;
	
	public ModifyGui (String modifyingId) {
		this.modifyingId = modifyingId;
	}

	@Override
	public Inventory getInventory() {
		Inventory gui = Bukkit.createInventory(this, 54,
				ChatColor.DARK_BLUE + "MyBlocks Modifier - " + modifyingId);
		
		ItemStack items = MyBlocksConfig.getTool(modifyingId).clone();
		ItemMeta meta = items.getItemMeta();
		
		int blockId = MyBlocksConfig.getBlockId(modifyingId);
		
		List<String> lores = new ArrayList<String>();
		if (items.getItemMeta().getLore() != null)
			lores = items.getItemMeta().getLore();
		
		lores.add(ChatColor.BLUE + "---------------");
		lores.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "Id: " + modifyingId);
		lores.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "BlockId: " + blockId);
		
		meta.setLore(lores);
		items.setItemMeta(meta);
		
		gui.setItem(13, items);
		
		gui.setItem(45, GuiButton.getReturn());
		
		gui.setItem(28, GuiButton.getBlockId());
		gui.setItem(30, GuiButton.getId());
		gui.setItem(32, GuiButton.getModifyLoots());
		gui.setItem(34, GuiButton.getRemoval());
		
		return gui;
	}

	@Override
	public void onGuiClick(Player player, int slot, ItemStack clicked, ClickType clickType) {
		
		if (clicked == null || clicked.getType() == Material.AIR) return;
		
		if (clicked.equals(GuiButton.getRemoval())) {
			// ConfirmGui for MyBlocks removal
			player.openInventory(new ConfirmGui(modifyingId).getInventory());
		}
		else if (clicked.equals(GuiButton.getBlockId())) {
			// Open Sign GUI for modifying the Block Id
			player.sendMessage(ChatColor.GREEN + "Please enter the new Block Id of MyBlocks.");
			
			SignGUI signgui = new SignGUI()
					.line(0, "")
					.line(1, "^^^^^^^^^^^^^^^^")
					.line(2, "Please enter")
					.line(3, "new block id")
					.biConsumer((player0, lines) -> {
						if (lines[0] == null || lines[0].isEmpty()) {
							player0.sendMessage(ChatColor.RED + "Empty block id entered. Nothing changed.");
						}
						else {
							if (!Utils.isInteger(lines[0]))
								player0.sendMessage(ChatColor.RED + "Cannot read this block id. Nothing changed.");
							else
								MyBlocksConfig.setBlockId(modifyingId, Integer.valueOf(lines[0]));
						}
						player.openInventory(new ModifyGui(modifyingId).getInventory());
					});
			signgui.show(player);
		}
		else if (clicked.equals(GuiButton.getId())) {
			// TODO - Open Sign GUI for modifying the Id
			player.sendMessage(ChatColor.GREEN + "Please enter the new Id of MyBlocks.");
			
			SignGUI signgui = new SignGUI()
					.line(0, "")
					.line(1, "^^^^^^^^^^^^^^^^")
					.line(2, "Please enter")
					.line(3, "new id")
					.biConsumer((player0, lines) -> {
						if (lines[0] == null || lines[0].isEmpty()) {
							player0.sendMessage(ChatColor.RED + "Empty id entered. Nothing changed.");
						}
						else {
							if (MyBlocksConfig.isIdExist(lines[0]))
								player0.sendMessage(ChatColor.RED + "A block with this id already exist. Please choose another one.");
							else
								MyBlocksConfig.setId(modifyingId, lines[0]);
						}
						player.openInventory(new ModifyGui(lines[0]).getInventory());
					});
			signgui.show(player);
		}
		else if (clicked.equals(GuiButton.getModifyLoots())) {
			// LootsGui for viewing MyBlocks loot
			player.openInventory(new LootsGui(modifyingId).getInventory());
		}
		
		if (slot >= 45 && slot <= 54) {
			if (clicked.equals(GuiButton.getReturn())) {
				player.openInventory(new BlockChooserGui(1).getInventory());
			}
		}
	}
}
