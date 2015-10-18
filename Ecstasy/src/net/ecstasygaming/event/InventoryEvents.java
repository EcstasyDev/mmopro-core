package net.ecstasygaming.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

import net.ecstasygaming.Ecstasy;
import net.ecstasygaming.objects.DropType;
import net.ecstasygaming.objects.EcstasyItem;
import net.ecstasygaming.util.MessageType;
import net.ecstasygaming.util.Messenger;

public class InventoryEvents implements Listener {

	Ecstasy plugin;
	public InventoryEvents(Ecstasy plugin)
	{
		Ecstasy.log.info("Linked Inventory Events handler.");
		this.plugin = plugin;
	}
	
	private HashMap<String,List<ItemStack>> saved_items = new HashMap<String,List<ItemStack>>();
	
	@EventHandler (priority = EventPriority.LOWEST)
	public void onItemDrop(PlayerDropItemEvent event)
	{
		for(EcstasyItem e : Ecstasy.items.values())
		{
			if(e.getItemStack().getItemMeta().getDisplayName() == event.getItemDrop().getItemStack().getItemMeta().getDisplayName())
			{
				if(e.getDropType() == DropType.DISALLOW_DROP)
				{
					Messenger.toPlayer(event.getPlayer(), "This item is soulbound and cannot be dropped.", MessageType.WARNING);
					Messenger.toPlayer(event.getPlayer(), "Hint: You can use /destroy to destroy it entirely.", MessageType.WARNING);
					event.setCancelled(true); // Cancel the event
					
					event.getItemDrop().remove(); // Removes the item from existence
				}
			}
		}
	}
	
	@EventHandler (priority = EventPriority.LOW)
	public void onPlayerDeath_DROP(PlayerDeathEvent event)
	{
		// Cycle through player's items and check for soulbound items
		// Player will keep their soulbound items and lose everything else (Minecraft PVP Feature)
		Player p = event.getEntity();
		List<ItemStack> saved = new ArrayList<ItemStack>();
		// Normal inventory contents
		for(ItemStack is : event.getDrops())
		{
			for(EcstasyItem e : Ecstasy.items.values())
			{
				if(e.getItemStack().getItemMeta().getDisplayName() == is.getItemMeta().getDisplayName())
				{
					if(e.getDropType() == DropType.ALLOW_DROP)
					{
						// These items are soulbound items only - non-soulbound items get dropped
						saved.add(is);						
					}
				}
			}
		}
		
		event.getDrops().clear();
		
		saved_items.put(p.getName(), saved);
	}
	@EventHandler (priority = EventPriority.HIGHEST)
	public void onPlayerRespawn_REGAIN_SB(PlayerRespawnEvent event)
	{
		if(saved_items.containsKey(event.getPlayer()))
		{
			if(saved_items.get(event.getPlayer().getName()).size() > 0)
			{
				for(ItemStack is : saved_items.get(event.getPlayer().getName()))
				{
					event.getPlayer().getInventory().addItem(is);
				}
				
				Messenger.toPlayer(event.getPlayer(), "Soulbound items have been returned to you.", MessageType.MESSAGE);
			}
		}
	}
	
}
