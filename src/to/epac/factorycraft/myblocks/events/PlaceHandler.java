package to.epac.factorycraft.myblocks.events;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.MultipleFacing;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import to.epac.factorycraft.myblocks.MyBlocks;
import to.epac.factorycraft.myblocks.config.MyBlocksConfig;
import to.epac.factorycraft.myblocks.config.MyBlocksConfig.SoundType;
import to.epac.factorycraft.myblocks.utils.MyBlocksUtils;

public class PlaceHandler implements Listener {
	
	Plugin plugin = MyBlocks.inst();
	
	@EventHandler
	public void onPlace(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		ItemStack item = player.getInventory().getItemInMainHand();
		
		Block block = event.getClickedBlock();
		if (block == null) return;
		
		Block relative = block.getRelative(event.getBlockFace());
		if (relative == null) return;
		
		Location playerBlockLoc = player.getLocation().getBlock().getLocation();
        Location relativeLoc = relative.getLocation();
		
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        
        
        
        for (String id : MyBlocksConfig.getBlocks()) {
        	ItemStack tool = MyBlocksConfig.getTool(id);
        	
        	if (item.equals(tool)) {
        		event.setCancelled(true);
        		
        		// TODO - Calculate facing's coordinate, then add/subtract player's body(0.3)
        		// and check if the player is in 0.8 range, if yes, we are not placing
        		if (playerBlockLoc.equals(relativeLoc)) return;
        		
        		MyBlocksUtils.playSound(player, id, SoundType.PLACE);
        		
        		
        		
        		int blockid = MyBlocksConfig.getBlockId(id);
                if (blockid < 54)
                    relative.setType(Material.BROWN_MUSHROOM_BLOCK);
                else if (blockid < 101)
                    relative.setType(Material.RED_MUSHROOM_BLOCK);
                else if (blockid < 161)
                    relative.setType(Material.MUSHROOM_STEM);
                
                
        		
                BlockData blockData = relative.getBlockData();
                MultipleFacing multiFacing = (MultipleFacing) blockData;

                Boolean[] faces = MyBlocksUtils.getFacingById(blockid);

                multiFacing.setFace(BlockFace.DOWN , faces[0]);
                multiFacing.setFace(BlockFace.EAST , faces[1]);
                multiFacing.setFace(BlockFace.NORTH, faces[2]);
                multiFacing.setFace(BlockFace.SOUTH, faces[3]);
                multiFacing.setFace(BlockFace.UP   , faces[4]);
                multiFacing.setFace(BlockFace.WEST , faces[5]);
        		
                relative.setBlockData(multiFacing);

                if (player.getGameMode() != GameMode.CREATIVE)
                    player.getInventory().getItemInMainHand().setAmount(item.getAmount() - 1);
                
                break;
        	}
        }
	}
}
