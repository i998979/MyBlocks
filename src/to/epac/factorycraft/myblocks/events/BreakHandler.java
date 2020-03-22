package to.epac.factorycraft.myblocks.events;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.MultipleFacing;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import to.epac.factorycraft.myblocks.config.MyBlocksConfig;
import to.epac.factorycraft.myblocks.config.MyBlocksConfig.SoundType;
import to.epac.factorycraft.myblocks.utils.MyBlocksUtils;

public class BreakHandler implements Listener {
	
	@EventHandler
	public void onBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();
		ItemStack item = player.getInventory().getItemInMainHand();
		Block block = event.getBlock();
		
		if (player.getGameMode() == GameMode.CREATIVE) return;
		if (block.getType() != Material.BROWN_MUSHROOM_BLOCK
				&& block.getType() != Material.MUSHROOM_STEM
				&& block.getType() != Material.RED_MUSHROOM_BLOCK) return;
		
		BlockData blockData = block.getBlockData().clone();
		MultipleFacing multiFacing = (MultipleFacing) blockData;
		MultipleFacing pseudoFacing = (MultipleFacing) blockData.clone();
		
		event.setDropItems(false);
		
		if (item.getItemMeta() instanceof Damageable) {
			if (!item.getItemMeta().isUnbreakable()) {
				Damageable dmgable = (Damageable) item.getItemMeta();
				
				dmgable.setDamage(dmgable.getDamage() + 1);
				item.setItemMeta((ItemMeta) dmgable);
			}
		}
		
		// Loop through all the Mushroom Blocks
		for (int blockid = 1; blockid < 160; blockid++) {
			// Get the facing list of the Mushroom Block
			Boolean[] faces = MyBlocksUtils.getFacingById(blockid);
			
			pseudoFacing.setFace(BlockFace.DOWN , faces[0]);
			pseudoFacing.setFace(BlockFace.EAST , faces[1]);
			pseudoFacing.setFace(BlockFace.NORTH, faces[2]);
			pseudoFacing.setFace(BlockFace.SOUTH, faces[3]);
			pseudoFacing.setFace(BlockFace.UP   , faces[4]);
			pseudoFacing.setFace(BlockFace.WEST , faces[5]);
			
			// If block's facing equals to specified id's facing
			if (multiFacing.matches(pseudoFacing)) {
				// Loop through all the MyBlocks
				for (String id : MyBlocksConfig.getBlocks()) {
					
					// If block's id equals to the one you are breaking
					if (MyBlocksConfig.getBlockId(id) == blockid) {
						MyBlocksUtils.playSound(player, id, SoundType.BREAK);
						
						if (MyBlocksConfig.getLoots(id) == null) break;
						
						for (ItemStack loot : MyBlocksConfig.getLoots(id)) {
							// Drop the loot on ground
							block.getWorld().dropItemNaturally(block.getLocation(), loot);
						}
						break;
					}
				}
				break;
			}
		}
	}	
}
