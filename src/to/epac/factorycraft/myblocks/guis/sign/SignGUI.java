package to.epac.factorycraft.myblocks.guis.sign;

import java.lang.reflect.InvocationTargetException;
import java.util.function.BiConsumer;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.events.PacketListener;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.nbt.NbtCompound;
import com.comphenix.protocol.wrappers.nbt.NbtFactory;

import to.epac.factorycraft.myblocks.MyBlocks;

public class SignGUI {
	private MyBlocks plugin = MyBlocks.inst();
	
	private String[] display;
	private Location loc;
	private Block block;
	
	private BiConsumer<Player, String[]> biConsumer;
	private PacketListener listener;
	
	public SignGUI line(int line, String text) {
		if (display == null) display = new String[4];
		
		display[line] = ChatColor.translateAlternateColorCodes('&', text);
		
		return this;
	}
	
	public SignGUI biConsumer(BiConsumer<Player, String[]> biConsumer) {
		this.biConsumer = biConsumer;
		
		return this;
	}
	
	public void show(Player player) {
		loc = player.getLocation();
		loc.setY(0);
		block = loc.getBlock();
		
		player.sendBlockChange(loc, Material.OAK_SIGN.createBlockData());
		
		PacketContainer openSignEditor = MyBlocks.protocolManager.createPacket(
			PacketType.Play.Server.OPEN_SIGN_EDITOR);
		
		BlockPosition blockPosition = new BlockPosition(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
		openSignEditor.getBlockPositionModifier().write(0, blockPosition);
		
		PacketContainer tileEntityData = MyBlocks.protocolManager.createPacket(
			PacketType.Play.Server.TILE_ENTITY_DATA);
		
		NbtCompound signNbt = NbtFactory.ofCompound("MyBlocks");
		signNbt.put("Text1", "{\"text\":\"" + display[0] + "\"}");
		signNbt.put("Text2", "{\"text\":\"" + display[1] + "\"}");
		signNbt.put("Text3", "{\"text\":\"" + display[2] + "\"}");
		signNbt.put("Text4", "{\"text\":\"" + display[3] + "\"}");
		signNbt.put("id", "minecraft:sign");
		signNbt.put("x", loc.getBlockX());
		signNbt.put("y", loc.getBlockY());
		signNbt.put("z", loc.getBlockZ());
		
		tileEntityData.getBlockPositionModifier().write(0, blockPosition);
		tileEntityData.getIntegers().write(0, 9);
		tileEntityData.getNbtModifier().write(0, signNbt);
		
		try {
			MyBlocks.protocolManager.sendServerPacket(player, tileEntityData);
			MyBlocks.protocolManager.sendServerPacket(player, openSignEditor);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
		MyBlocks.protocolManager.addPacketListener(listener = new PacketAdapter(plugin, PacketType.Play.Client.UPDATE_SIGN) {
			@Override
				public void onPacketReceiving(PacketEvent event) {
				PacketContainer packet = event.getPacket();
				Player player = event.getPlayer();
				String[] input = packet.getStringArrays().read(0);
				
				player.sendBlockChange(loc, block.getBlockData());
				
				unregisterListener();
				
				StringBuilder builder = new StringBuilder();
				for (int i = 0; i < input.length; i++) {
					builder.append(input[i]);
					if (i < input.length - 1)
						builder.append(" ");
				}
				
				Bukkit.getScheduler().runTask(plugin, (Runnable)() -> {
					biConsumer.accept(player, input);
				});
			}
		});
	}
	
	public void unregisterListener() {
		MyBlocks.protocolManager.removePacketListener(listener);
	}
}
