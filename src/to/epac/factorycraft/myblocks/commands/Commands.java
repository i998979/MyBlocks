package to.epac.factorycraft.myblocks.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import to.epac.factorycraft.myblocks.config.MyBlocksConfig;
import to.epac.factorycraft.myblocks.gui.BlockChooserGui;
import to.epac.factorycraft.myblocks.utils.Utils;

public class Commands implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!sender.hasPermission("MyBlocks.Admin")) {
			sender.sendMessage(ChatColor.RED + "You don't have permission to perform this command.");
			return false;
		}	
		if (args.length == 0) {
			HelpPage(sender);
			return false;
		}
		if (args[0].equalsIgnoreCase("help")) {
			HelpPage(sender);
		}
		else if (args[0].equalsIgnoreCase("create")) {
			if (args.length == 1) {
				sender.sendMessage(ChatColor.RED + "Please enter the Id.");
				return false;
			}
			if (MyBlocksConfig.isIdExist(args[1])) {
				sender.sendMessage(ChatColor.RED + "The Id you have entered already exist.");
				return false;
			}
			if (args.length == 2) {
				sender.sendMessage(ChatColor.RED + "Please enter the Block Id.");
				return false;
			}
			if (!Utils.isInteger(args[2])) {
				sender.sendMessage(ChatColor.RED + "Block Id should be an integer.");
				return false;
			}
			if (Integer.valueOf(args[2]) < 1 || Integer.valueOf(args[2]) > 159) {
				sender.sendMessage(ChatColor.RED + "Block Id should between 1 and 159.");
				return false;
			}
			MyBlocksConfig.create(args[1], Integer.valueOf(args[2]));
			sender.sendMessage(ChatColor.GREEN + "Created MyBlocks named \"" + ChatColor.YELLOW
				+ args[1] + ChatColor.GREEN + "\". Now type "
				+ ChatColor.YELLOW + "/mb setTool " + args[1]
				+ ChatColor.GREEN + " to add your held item as tool to place the block.");
			}
		else if (args[0].equalsIgnoreCase("getBlockId")) {
			if (args.length == 1) {
				sender.sendMessage(ChatColor.RED + "Please enter the Id.");
				return false;
			}
			if (!MyBlocksConfig.isIdExist(args[1])) {
				sender.sendMessage(ChatColor.RED + "The Id you have entered does not exist.");
				return false;
			}
			if (MyBlocksConfig.getBlockId(args[1]) == 0) {
				sender.sendMessage(ChatColor.RED + "The Id you have entered does not have any Block Id.");
				return false;
			}
			sender.sendMessage(ChatColor.GREEN + "Block Id of \"" + ChatColor.YELLOW
				+ args[1] + ChatColor.GREEN + "\" is "
				+ ChatColor.YELLOW + MyBlocksConfig.getBlockId(args[1]) + ChatColor.GREEN + ".");
		}
		else if (args[0].equalsIgnoreCase("setBlockId")) {
			if (args.length == 1) {
				sender.sendMessage(ChatColor.RED + "Please enter the Id.");
				return false;
			}
			if (!MyBlocksConfig.isIdExist(args[1])) {
				sender.sendMessage(ChatColor.RED + "The Id you have entered does not exist.");
				return false;
			}
			if (args.length == 2) {
				sender.sendMessage(ChatColor.RED + "Please enter the Block Id.");
				return false;
			}
			if (!Utils.isInteger(args[2])) {
				sender.sendMessage(ChatColor.RED + "Block Id should be an integer.");
				return false;
			}
			if (Integer.valueOf(args[2]) < 1 || Integer.valueOf(args[2]) > 159) {
				sender.sendMessage(ChatColor.RED + "Block Id should between 1 and 159.");
				return false;
			}
			if (MyBlocksConfig.isBlockIdExist(Integer.valueOf(args[2]))) {
				sender.sendMessage(ChatColor.RED + "The Block Id you have entered already exist.");
				return false;
			}
			MyBlocksConfig.setBlockId(args[1], Integer.valueOf(args[2]));
			sender.sendMessage(ChatColor.GREEN + "Set \""
				+ ChatColor.YELLOW + args[1] + ChatColor.GREEN + "\" Block Id to "
				+ ChatColor.YELLOW + args[2] + ChatColor.GREEN + ".");
		}
		else if (args[0].equalsIgnoreCase("gui")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED + "You must be a player to execute this command.");
				return false;
			}
			Player player = (Player) sender;
			
			player.sendMessage(ChatColor.GREEN + "Opening MyBlocks Gui.");
			player.openInventory(new BlockChooserGui().getInventory());
		}
		else if (args[0].equalsIgnoreCase("getTool")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED + "You must be a player to execute this command.");
				return false;
			}
			Player player = (Player) sender;
			
			if (args.length == 1) {
				player.sendMessage(ChatColor.RED + "Please enter the Id.");
				return false;
			}
			if (!MyBlocksConfig.isIdExist(args[1])) {
				player.sendMessage(ChatColor.RED + "The Id you have entered does not exist.");
				return false;
			}
			if (MyBlocksConfig.getTool(args[1]) == null) {
				player.sendMessage(ChatColor.RED + "The Id you have entered does not have any tools.");
				return false;
			}
			player.getInventory().addItem(MyBlocksConfig.getTool(args[1]));
			
			player.sendMessage(ChatColor.GREEN + "You have been given a \""
				+ ChatColor.YELLOW + args[1]
				+ ChatColor.GREEN + "\". Right click to place it.");
			
		}
		else if (args[0].equalsIgnoreCase("setTool")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED + "You must be a player to execute this command.");
				return false;
			}
			Player player = (Player) sender;
			
			if (args.length == 1) {
				player.sendMessage(ChatColor.RED + "Please enter the Id.");
				return false;
			}
			if (!MyBlocksConfig.isIdExist(args[1])) {
				player.sendMessage(ChatColor.RED + "The Id you have entered does not exist.");
				return false;
			}
			if (player.getInventory().getItemInMainHand().getType() == Material.AIR) {
				player.sendMessage(ChatColor.RED + "You cannot set AIR as tool.");
				return false;
			}
			MyBlocksConfig.setTool(args[1], player.getInventory().getItemInMainHand());
			player.sendMessage(ChatColor.GREEN + "Set your held item as tool.");
			
		}
		else if (args[0].equalsIgnoreCase("removeTool")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED + "You must be a player to execute this command.");
				return false;
			}
			Player player = (Player) sender;
			
			if (args.length == 1) {
				player.sendMessage(ChatColor.RED + "Please enter the Id.");
				return false;
			}
			if (!MyBlocksConfig.isIdExist(args[1])) {
				player.sendMessage(ChatColor.RED + "The Id you have entered does not exist.");
				return false;
			}
			if (MyBlocksConfig.getTool(args[1]) != null) {
				player.sendMessage(ChatColor.YELLOW + args[1] + ChatColor.RED + " doesn't have any tool.");
				return false;
			}
			player.sendMessage(ChatColor.GREEN + "Removed " + ChatColor.YELLOW + args[1] + ChatColor.GREEN + "'s tool.");
			MyBlocksConfig.removeTool(args[1]);
		}
		else if (args[0].equalsIgnoreCase("getLoots")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED + "You must be a player to execute this command.");
				return false;
			}
			Player player = (Player) sender;
			
			if (args.length == 1) {
				player.sendMessage(ChatColor.RED + "Please enter the Id.");
				return false;
			}
			if (!MyBlocksConfig.isIdExist(args[1])) {
				player.sendMessage(ChatColor.RED + "The Id you have entered does not exist.");
				return false;
			}
			if (MyBlocksConfig.getLoots(args[1]) == null) {
				player.sendMessage(ChatColor.RED + "No loots found.");
				return false;
			}
			player.sendMessage(ChatColor.BLUE + "-------------[" + ChatColor.YELLOW + "Loot List" + ChatColor.BLUE + "]-------------");
			for (ItemStack loot : MyBlocksConfig.getLoots(args[1])) {
				player.sendMessage(ChatColor.GREEN + "" + loot.getAmount() + "x " +
					ChatColor.YELLOW + loot.getType() + ChatColor.GREEN + " named \"" + 
					ChatColor.BLUE + loot.getItemMeta().getDisplayName() + ChatColor.GREEN + "\".");
			}
			player.sendMessage(ChatColor.BLUE + "----------[" + ChatColor.YELLOW + "End of Loot List" + ChatColor.BLUE + "]----------");
		}
		else if (args[0].equalsIgnoreCase("addLoots")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED + "You must be a player to execute this command.");
				return false;
			}
			Player player = (Player) sender;
			
			if (args.length == 1) {
				player.sendMessage(ChatColor.RED + "Please enter the Id.");
				return false;
			}
			if (!MyBlocksConfig.isIdExist(args[1])) {
				player.sendMessage(ChatColor.RED + "The Id you have entered does not exist.");
				return false;
			}
			if (player.getInventory().getItemInMainHand().getType() == Material.AIR) {
				player.sendMessage(ChatColor.RED + "You cannot add AIR into Loot List.");
				return false;
			}
			MyBlocksConfig.addLoots(args[1], player.getInventory().getItemInMainHand());
			player.sendMessage(ChatColor.GREEN + "Added your held item into Loot List.");
		}
		else if (args[0].equalsIgnoreCase("removeLoots")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED + "You must be a player to execute this command.");
				return false;
			}
			Player player = (Player) sender;
			
			if (args.length == 1) {
				player.sendMessage(ChatColor.RED + "Please enter the Id.");
				return false;
			}
			if (!MyBlocksConfig.isIdExist(args[1])) {
				player.sendMessage(ChatColor.RED + "The Id you have entered does not exist.");
				return false;
			}
			if (player.getInventory().getItemInMainHand().getType() == Material.AIR) {
				player.sendMessage(ChatColor.RED + "You cannot remove AIR from Loot List.");
				return false;
			}
			MyBlocksConfig.removeLoots(args[1], player.getInventory().getItemInMainHand());
			player.sendMessage(ChatColor.GREEN + "Removed your held item from Loot List.");
		}
		else {
			HelpPage(sender);
		}
		
		return false;
	}
	public static void HelpPage(CommandSender sender) {
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&5--------------------&9[&eMyBlocks&9]&5--------------------"));
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&dMain command: /mb, /mblock, /mbs"));
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c<>: Required &d[]: Optional"));
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b/MyBlocks &d[arg]&b: &3Base command."));
		
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bGui : &3Open up Block Chooser Gui."));
		
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bCreate &c<id> &c<Block Id (1-159)>&b: &3Create MyBlocks with specified Id and Block Id."));

		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bgetBlockId &c<id>&b: &3Get the saved block Id."));
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bsetBlockId &c<id> <Block Id(1-159)>&b: &3Set the block Id."));
		
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bgetTool &c<id>&b: &3Get tool that can place specified block."));
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bsetTool &c<id>&b: &3Set your held-item as tool that can place specified block."));
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bremoveTool &c<id>&b: &3Remove the tool that can place specified block."));
		
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&baddLoots &c<id>&b: &3Add held item to specified block's Loot List."));
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bremoveLoots &c<id>&b: &3Remove held item from specified block's Loot List."));
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bgetLoots &c<id>&b: &3Get specified block's Loot List."));
		
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&5--------------------&9[&eMyBlocks&9]&5--------------------"));
	}
}
