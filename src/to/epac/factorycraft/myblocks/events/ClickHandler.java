package to.epac.factorycraft.myblocks.events;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.MultipleFacing;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import to.epac.factorycraft.myblocks.config.MyBlocksConfig;
import to.epac.factorycraft.myblocks.config.MyBlocksConfig.SoundType;
import to.epac.factorycraft.myblocks.utils.MyBlocksUtils;

public class ClickHandler implements Listener {
	
	@EventHandler
	public void onClick(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		Action action = event.getAction();
		Block block = event.getClickedBlock();
		
		if (action != Action.LEFT_CLICK_BLOCK && action != Action.RIGHT_CLICK_BLOCK) return;
		if (block.getType() != Material.BROWN_MUSHROOM_BLOCK
				&& block.getType() != Material.MUSHROOM_STEM
				&& block.getType() != Material.RED_MUSHROOM_BLOCK) return;
		if (event.getHand() != EquipmentSlot.HAND) return;
		
		
		
		BlockData blockdata = block.getBlockData();
		MultipleFacing multifacing = (MultipleFacing) blockdata;
		
		int blockId = MyBlocksUtils.getIdByFacing(multifacing.getFaces());
		if (blockId < 0) return;
		
		String id = MyBlocksConfig.getId(blockId);
		if (id == null) return;
		
		List<String> cmds = new ArrayList<>();
		switch (action) {
		case LEFT_CLICK_BLOCK:
			if (!player.isSneaking()) {
				MyBlocksUtils.playSound(player, id, SoundType.LEFT_SNEAK);
				cmds.addAll(MyBlocksConfig.getLeftCommands(id));
			}
			else {
				MyBlocksUtils.playSound(player, id, SoundType.LEFT);
				cmds.addAll(MyBlocksConfig.getLeftSneakCommands(id));
			}
			break;
		case RIGHT_CLICK_BLOCK:
			if (!player.isSneaking()) {
				MyBlocksUtils.playSound(player, id, SoundType.RIGHT);
				cmds.addAll(MyBlocksConfig.getRightCommands(id));
			}
			else {
				MyBlocksUtils.playSound(player, id, SoundType.RIGHT_SNEAK);
				cmds.addAll(MyBlocksConfig.getRightSneakCommands(id));
			}
			break;
		default:
			return;
		}
		
		
		
		for (String cmd : cmds) {
			   cmd = cmd.replaceAll("%player%", player.getName());
			   cmd = ChatColor.translateAlternateColorCodes('&', cmd);
			   if (cmd.startsWith("c:"))
				   Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), cmd.substring(2));
			   else
				   player.performCommand(cmd);
		}
	}
}
