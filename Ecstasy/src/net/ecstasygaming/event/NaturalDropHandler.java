package net.ecstasygaming.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.inventory.ItemStack;

import net.ecstasygaming.MMOPro;
import net.ecstasygaming.objects.EcstasyItem;

public class NaturalDropHandler implements Listener {

	MMOPro plugin;
	public NaturalDropHandler(MMOPro plugin)
	{
		this.plugin = plugin;
		MMOPro.log.info("Registered Natural Drop Handler.");
	}
	
	@EventHandler (priority = EventPriority.LOW)
	public void onNaturalItemSpawn(ItemSpawnEvent event)
	{
		ItemStack is = event.getEntity().getItemStack();
		int amount = is.getAmount();
		
		EcstasyItem ei = null;
		// Attempt to locate the item from the master list of modified items
		for(String k : MMOPro.items.keySet())
		{
			if(MMOPro.items.get(k).getItemStack().getType() == is.getType())
			{ // item is located
				ei = MMOPro.items.get(k);
			}
		}
		if(ei != null)
		{
			int i = 0;
			while(i < amount)
			{
				i++;
			}
		}
		
		event.setCancelled(true); // Cancels normal drops
	}
}
