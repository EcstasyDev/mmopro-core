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
	
	public static HashMap<String,List<ItemStack>> saved_items = new HashMap<String,List<ItemStack>>();
	
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
	
	@EventHandler (priority = EventPriority.HIGHEST)
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
					if(e.getDropType() == DropType.DISALLOW_DROP)
					{
						// These items are soulbound items only - non-soulbound items get dropped
						
						is.setDurability((short) (is.getDurability() - (is.getType().getMaxDurability()/8)));
						
						saved.add(is);
						
						Ecstasy.log.info("Saved Soulbound Item: " + is.getItemMeta().getDisplayName());
					}
					else
					{
						p.getWorld().dropItemNaturally(p.getLocation(), is);
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
		if(InventoryEvents.saved_items.containsKey(event.getPlayer().getName()))
		{
			ItemStack is = null;
			int size = InventoryEvents.saved_items.get(event.getPlayer().getName()).size();
			
			if(size > 0)
			{
				for(int i = 0; i < size; i++)
				{
					is = InventoryEvents.saved_items.get(event.getPlayer().getName()).get(i);
					
					event.getPlayer().getInventory().addItem(is);
				}
				
				Messenger.toPlayer(event.getPlayer(), "Soulbound items have been returned to your inventory.", MessageType.MESSAGE);
			}
		}
		
	}
	
}
