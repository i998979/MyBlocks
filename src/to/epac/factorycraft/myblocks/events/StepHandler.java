package to.epac.factorycraft.myblocks.events;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.MultipleFacing;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import to.epac.factorycraft.myblocks.config.MyBlocksConfig;
import to.epac.factorycraft.myblocks.config.MyBlocksConfig.SoundType;
import to.epac.factorycraft.myblocks.utils.MyBlocksUtils;

public class StepHandler implements Listener {
	
	@EventHandler
	public void onStep(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		Location from = event.getFrom().clone().subtract(0, 1, 0);
		Location to = event.getTo().clone().subtract(0, 1, 0);
		Block block = to.getBlock();
		
		if (block.getType() != Material.BROWN_MUSHROOM_BLOCK
				&& block.getType() != Material.MUSHROOM_STEM
				&& block.getType() != Material.RED_MUSHROOM_BLOCK) return;
		if (from.distance(to) <= 0.2) return;
		if (!player.isOnGround()) return;
		
		BlockData blockdata = block.getBlockData();
		MultipleFacing multifacing = (MultipleFacing) blockdata;
		
		int blockId = MyBlocksUtils.getIdByFacing(multifacing.getFaces());
		if (blockId < 0) return;
		
		String id = MyBlocksConfig.getId(blockId);
		if (id == null) return;
		
		MyBlocksUtils.playSound(player, id, SoundType.STEP);
	}
}
