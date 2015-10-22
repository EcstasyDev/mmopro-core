package net.ecstasygaming.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.inventory.ItemStack;

import net.ecstasygaming.Ecstasy;
import net.ecstasygaming.objects.EcstasyItem;

public class NaturalDropHandler implements Listener {

	Ecstasy plugin;
	public NaturalDropHandler(Ecstasy plugin)
	{
		this.plugin = plugin;
		Ecstasy.log.info("Registered Natural Drop Handler.");
	}
	
	@EventHandler (priority = EventPriority.LOW)
	public void onNaturalItemSpawn(ItemSpawnEvent event)
	{
		ItemStack is = event.getEntity().getItemStack();
		int amount = is.getAmount();
		
		EcstasyItem ei = null;
		// Attempt to locate the item from the master list of modified items
		for(String k : Ecstasy.items.keySet())
		{
			if(Ecstasy.items.get(k).getItemStack().getType() == is.getType())
			{ // item is located
				ei = Ecstasy.items.get(k);
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
