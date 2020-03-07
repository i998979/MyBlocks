package to.epac.factorycraft.myblocks.events;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;

public class PhysicsHandler implements Listener {
	
	@EventHandler
	public void onPhysics(BlockPhysicsEvent event) {
		Material type = event.getChangedType();
		
		if (type != Material.BROWN_MUSHROOM_BLOCK
				&& type != Material.MUSHROOM_STEM
				&& type != Material.RED_MUSHROOM_BLOCK) return;
		
		event.setCancelled(true);
	}
}
