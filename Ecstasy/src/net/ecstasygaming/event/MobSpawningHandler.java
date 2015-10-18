package net.ecstasygaming.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import net.ecstasygaming.Ecstasy;

public class MobSpawningHandler implements Listener {
	
	Ecstasy plugin;
	public MobSpawningHandler(Ecstasy plugin)
	{
		Ecstasy.log.info("Linked Mob Spawning handler.");
		this.plugin = plugin;
	}
	
	@EventHandler (priority = EventPriority.NORMAL)
	public void onCreatureSpawn(CreatureSpawnEvent event)
	{
		
	}	
	@EventHandler (priority = EventPriority.LOW)
	public void OnEntityDamage(EntityDamageByEntityEvent event)
	{
		
	}
	@EventHandler (priority = EventPriority.LOW)
	public void OnEntityDeath(EntityDeathEvent event)
	{
		
	}
}
