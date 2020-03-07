package to.epac.factorycraft.myblocks.gui;

import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GuiButton {
	
	public static ItemStack getPrevPage() {
		ItemStack prevPage = new ItemStack(Material.PAPER, 1);
		ItemMeta prevMeta = prevPage.getItemMeta();
		prevMeta.setDisplayName(ChatColor.RED + "Previous Page");
		prevPage.setItemMeta(prevMeta);
		
		return prevPage;
	}
	public static ItemStack getPrevPage0(int curr, int max) {
		ItemStack prevPage = new ItemStack(Material.PAPER, 1);
		ItemMeta prevMeta = prevPage.getItemMeta();
		prevMeta.setDisplayName(ChatColor.RED + "Previous Page" + ChatColor.YELLOW + "(" + curr + "/" + max + ")");
		prevPage.setItemMeta(prevMeta);
		
		return prevPage;
	}
	
	public static ItemStack getCurrPage(int page) {
		ItemStack currPage = new ItemStack(Material.EMERALD, 1);
		ItemMeta currMeta = currPage.getItemMeta();
		//String name = ChatColor.GRAY + "Current Page: %page%/%max%";
		String name = ChatColor.GRAY + "Current Page: %page%";
		name = name.replaceAll("%page%", page + "");
		currMeta.setDisplayName(name);
		currPage.setItemMeta(currMeta);
		
		return currPage;
	}
	public static ItemStack getCurrPage(int page, int max) {
		ItemStack currPage = new ItemStack(Material.EMERALD, 1);
		ItemMeta currMeta = currPage.getItemMeta();
		//String name = ChatColor.GRAY + "Current Page: %page%/%max%";
		String name = ChatColor.GRAY + "Current Page: %page%";
		name = name.replaceAll("%page%", page + "").replaceAll("%max%", max + "");
		currMeta.setDisplayName(name);
		currPage.setItemMeta(currMeta);
		
		return currPage;
	}
	
	public static ItemStack getNextPage() {
		ItemStack nextPage = new ItemStack(Material.PAPER, 1);
		ItemMeta nextMeta = nextPage.getItemMeta();
		nextMeta.setDisplayName(ChatColor.GREEN + "Next Page");
		nextPage.setItemMeta(nextMeta);
		
		return nextPage;
	}
	public static ItemStack getNextPage0(int curr, int max) {
		ItemStack nextPage = new ItemStack(Material.PAPER, 1);
		ItemMeta nextMeta = nextPage.getItemMeta();
		nextMeta.setDisplayName(ChatColor.GREEN + "Next Page" + ChatColor.YELLOW + curr + "/" + max);
		nextPage.setItemMeta(nextMeta);
		
		return nextPage;
	}
	
	public static ItemStack getReturn() {
		ItemStack return0 = new ItemStack(Material.ARROW, 1);
		ItemMeta returnMeta = return0.getItemMeta();
		returnMeta.setDisplayName(ChatColor.GREEN + "Return");
		return0.setItemMeta(returnMeta);
		
		return return0;
	}
	
	public static ItemStack getClose() {
		ItemStack close = new ItemStack(Material.BARRIER, 1);
		ItemMeta closeMeta = close.getItemMeta();
		closeMeta.setDisplayName(ChatColor.RED + "Close");
		close.setItemMeta(closeMeta);
		
		return close;
	}
	
	public static ItemStack getBlockId() {
		ItemStack blockId = new ItemStack(Material.ANVIL, 1);
		ItemMeta blockIdMeta = blockId.getItemMeta();
		blockIdMeta.setDisplayName(ChatColor.GOLD + "Modify Block Id");
		blockId.setItemMeta(blockIdMeta);
		
		return blockId;
	}
	
	public static ItemStack getId() {
		ItemStack id = new ItemStack(Material.BOOK, 1);
		ItemMeta idMeta = id.getItemMeta();
		idMeta.setDisplayName(ChatColor.AQUA + "Modify Id");
		id.setItemMeta(idMeta);
		
		return id;
	}
	
	public static ItemStack getModifyLoots() {
		ItemStack modifyLoots = new ItemStack(Material.CHEST, 1);
		ItemMeta modifyLootsMeta = modifyLoots.getItemMeta();
		modifyLootsMeta.setDisplayName(ChatColor.YELLOW + "Modify loots");
		modifyLoots.setItemMeta(modifyLootsMeta);
		
		return modifyLoots;
	}
	
	public static ItemStack getRemoval() {
		ItemStack removal = new ItemStack(Material.REDSTONE_BLOCK, 1);
		ItemMeta removalMeta = removal.getItemMeta();
		removalMeta.setDisplayName(ChatColor.RED + "Remove");
		removal.setItemMeta(removalMeta);
		
		return removal;
	}
	
	public static ItemStack getConfirm() {
		ItemStack confirm = new ItemStack(Material.EMERALD_BLOCK, 1);
		ItemMeta confirmMeta = confirm.getItemMeta();
		confirmMeta.setDisplayName(ChatColor.GREEN + "Confirm");
		confirm.setItemMeta(confirmMeta);
		
		return confirm;
	}
	public static ItemStack getCancel() {
		ItemStack cancel = new ItemStack(Material.REDSTONE_BLOCK, 1);
		ItemMeta cancelMeta = cancel.getItemMeta();
		cancelMeta.setDisplayName(ChatColor.RED + "Cancel");
		cancel.setItemMeta(cancelMeta);
		
		return cancel;
	}
	
	public static ItemStack getEmpty(String... lore) {
		ItemStack empty = new ItemStack(Material.BARRIER, 1);
		ItemMeta emptyMeta = empty.getItemMeta();
		emptyMeta.setDisplayName(ChatColor.RED + "Empty!");
		emptyMeta.setLore(Arrays.asList(lore));
		empty.setItemMeta(emptyMeta);
		
		return empty;
	}
}
